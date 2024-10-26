package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class ReportEvent extends Request{
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events;
    //daty startu i konca

    public ReportEvent(List<Event> events,int stars) {
        super(stars);
        this.events = new ArrayList<>(events);
        this.events.sort(Comparator.comparing(Event::getStartTime));
    }

    public ReportEvent() {
    }

    public ReportEventDto toDto() {
        return new ReportEventDto(id,startTime(),endTime(),stars);
    }

    @Override
    public LocalDateTime startTime() {
        return events.getFirst().getStartTime();
    }

    @Override
    public LocalDateTime endTime() {
        return events.getLast().getEndTime();
    }

    @Override
    public LocalDateTime endTimeBuffered() {
        return endTime().plusDays(1).withHour(6).withMinute(0).withSecond(0);
    }

    @Override
    public LocalDate startDate() {
        return events.getFirst().getStartTime().toLocalDate();
    }

    @Override
    public String toString() {
        return "EventRequest{" +
                "events=" + events +
                '}';
    }
}
