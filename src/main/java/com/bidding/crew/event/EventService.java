package com.bidding.crew.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EventService {
    private EventRepository eventRepository;
    private EventSpecificationBuilder eventSpecificationBuilder;

    public EventService(EventRepository eventRepository, EventSpecificationBuilder eventSpecificationBuilder) {
        this.eventRepository = eventRepository;
        this.eventSpecificationBuilder = eventSpecificationBuilder;
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

    public List<EventDto> getEventsStartingBefore(LocalDateTime startDate) {
        List<Event> eventsStartingBefore = eventRepository.findEventStartingBeforeDate(startDate);
        List<EventDto> results = new ArrayList<>();

        for (Event event : eventsStartingBefore) {
            if (event.getStartTime().isBefore(startDate)) {
                results.add(event.toDto());
            }
        }
        return results;
    }

    public List<String> getEventsDescriptionByPriority(int priority) {
        return eventRepository.findEventDescriptionByPriority(priority);
    }

    public List<EventDto> findEventsByParameters(LocalDateTime start, LocalDateTime end, Boolean reoccurring) {
        return eventRepository.findAllByParameters(start, end, reoccurring)
                .stream()
                .map(Event::toDto)
                .toList();
    }

    public List<String> getEventsDescriptionByPriority2(int priority) {
        return eventRepository.findEventsByPriority(priority)
                .stream()
                .map(Event::getDescription)
                .toList();
    }

    private Specification<Event> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("description"), "%event%");
        };
    }

    public List<EventDto> getEventsByName(){
        Specification<Event> specification = getSpecification();
        return eventRepository.findAll(specification)
                .stream()
                .map(Event::toDto)
                .toList();
    }

    private Specification<Event> getSpecification(SpecificationInput specificationInput) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(specificationInput.getColumnName()),
                specificationInput.getValue());
    }

    public List<Event> getEventsData(SpecificationInput specificationInput){
        Specification<Event> specification = getSpecification(specificationInput);
        return eventRepository.findAll(specification);
    }

    private Specification<Event> getEventsSpecificationStartingBetweenDates(SpecificationInput specificationInput) {
        String value = specificationInput.getValue();
        List<String> dateStrings = Arrays.asList(value.split(","));

        if (dateStrings.size() != 2) {
            throw new IllegalArgumentException("Invalid format of value. Expected two date-time values separated by a comma.");
        }

        LocalDateTime start = LocalDateTime.parse(dateStrings.get(0));
        LocalDateTime end = LocalDateTime.parse(dateStrings.get(1));
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(specificationInput.getColumnName()), start, end);
    }

    public List<Event> getEventsStartingBetweenDates(SpecificationInput specificationInput){
        Specification<Event> specification = getEventsSpecificationStartingBetweenDates(specificationInput);
        return eventRepository.findAll(specification);

    }

    public List<EventDto> findEventsByCriteria(SpecificationInput2 specificationInput2) {
        Specification<Event> specification = eventSpecificationBuilder.getSpecificationFor(specificationInput2);
        return eventRepository.findAll(specification)
                .stream()
                .map(Event::toDto)
                .toList();

    }
}
