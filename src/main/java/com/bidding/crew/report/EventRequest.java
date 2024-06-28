package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import com.bidding.crew.event.EventDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class EventRequest extends Request{
    private List<EventDto> events;

    public EventRequest(List<EventDto> events) {
        this.events = events;
        events.sort(Comparator.comparing(EventDto::getStartTime));
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
