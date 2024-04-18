package com.bidding.crew.event;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        System.out.println("event service created");
    }

    public void saveEvent(EventDto eventDto) {
        eventRepository.save(new Event(eventDto));
    }

    public List<EventDto> getAllEvents() {
        List<EventDto> eventDtos = new ArrayList<>();
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            eventDtos.add(new EventDto(event.getStartTime(),event.getEndTime(),
                    event.getPriority(),event.getDescription()));
        }
        return eventDtos;
    }

}
