package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class EventRequest extends Request{
    @OneToMany
    private List<Event> events;

    public EventRequest(List<Event> events) {
        this.events = new ArrayList<>(events);
        this.events.sort(Comparator.comparing(Event::getStartTime));
    }

    public EventRequest() {

    }

    public EventRequestDto toDto() {
        return new EventRequestDto(id, startTime(),endTime(),points);
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
