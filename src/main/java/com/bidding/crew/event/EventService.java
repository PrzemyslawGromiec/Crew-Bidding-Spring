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
            eventDtos.add(event.toDto());
        }
        return eventDtos;
    }

 /*   public List<EventDto> getReoccuringEvent(boolean reoccuring) {
        List<Event> availableEvents = eventRepository.findAll();
        List<EventDto> results = new ArrayList<>();

        for (Event availableEvent : availableEvents) {
            if (availableEvent.isReoccuring() == reoccuring) {
                results.add(availableEvent.toDto());
            }
        }
        return results;
    }*/

    public List<EventDto> getReoccurringEvent(boolean reoccurring) {
        List<Event> availableEvents = eventRepository.findByReoccurring(reoccurring);
        List<EventDto> results = new ArrayList<>();

        for (Event availableEvent : availableEvents) {
                results.add(availableEvent.toDto());
        }
        return results;
    }
}
