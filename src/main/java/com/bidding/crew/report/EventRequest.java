package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Entity
public class EventRequest extends Request{
    @OneToMany
    private List<Event> events;

    public EventRequest(List<Event> events) {
        this.events = events;
        events.sort(Comparator.comparing(Event::getStartTime));
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
}
