package com.bidding.crew.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventSpecificationBuilderImpl eventSpecificationBuilderImpl;
    private EventRepository eventRepository;
    private EventSpecificationBuilder eventSpecificationBuilder;

    public EventService(EventRepository eventRepository, EventSpecificationBuilder eventSpecificationBuilder, EventSpecificationBuilderImpl eventSpecificationBuilderImpl) {
        this.eventRepository = eventRepository;
        this.eventSpecificationBuilder = eventSpecificationBuilder;
        System.out.println("event service created");
        this.eventSpecificationBuilderImpl = eventSpecificationBuilderImpl;
    }

    public void saveEvent(EventDto eventDto) {
        eventRepository.save(new Event(eventDto));
    }

    public List<EventDto> findEventsByCriteria(SpecificationInput specificationInput) {
        Specification<Event> specification = eventSpecificationBuilderImpl.getSpecificationFor(specificationInput);
        return eventRepository.findAll(specification)
                .stream()
                .map(Event::toDto)
                .toList();
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public List<EventDto> getEventDtos() {
        return eventRepository.findAll()
                .stream()
                .map(Event::toDto)
                .toList();
    }
}
