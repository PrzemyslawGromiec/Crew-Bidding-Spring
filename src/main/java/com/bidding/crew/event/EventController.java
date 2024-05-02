package com.bidding.crew.event;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/api/v0/events")
    public void saveEvent(@RequestBody EventDto eventDto) {
        eventService.saveEvent(eventDto);
    }


    @GetMapping("/api/v0/ev")
    public List<EventDto> getReoccurringEvents(boolean reoccurring) {
        return eventService.getReoccurringEvent(reoccurring);
    }

    @GetMapping("/api/v0/events/before")
    public List<EventDto> getEventsBeforeDate(LocalDateTime startDate) {
        return eventService.getEventsStartingBefore(startDate);
    }

    @GetMapping("/api/v0/events")
    public List<EventDto> findEventsByCriteria(LocalDateTime startTime, LocalDateTime endTime, Boolean reoccurring) {
        return eventService.findEventsByParameters(startTime, endTime, reoccurring);
    }

    @GetMapping("api/v0/events/v2/")
    public List<EventDto> findEventsByCriteria(@RequestBody SpecificationInput2 specificationInput2) {//startTime,endTime,reocurring, description(like)
        return eventService.findEventsByCriteria(specificationInput2);
    }

    @GetMapping("/api/v0/events/descriptions")
    public List<String> getEventsDescriptionsByPriority(@RequestParam("priority") int priority) {
        return eventService.getEventsDescriptionByPriority(priority);
    }

    @GetMapping("/api/v0/events/descriptions2")
    public List<String> getEventsByPriorityDescriptionOnly(int priority) {
        return eventService.getEventsDescriptionByPriority2(priority);
    }

    @GetMapping("/api/v0/events/criteria")
    public List<EventDto> getByName() {
        return eventService.getEventsByName();
    }

    @GetMapping("/api/v0/events/byEquals")
    public List<EventDto> byEquals(@RequestBody SpecificationInput specificationInput) {
        return eventService.getEventsData(specificationInput)
                .stream()
                .map(Event::toDto)
                .toList();
    }

    @GetMapping("/api/v0/events/byBetweenDates")
    public List<EventDto> byBetweenDates(@RequestBody SpecificationInput specificationInput) {
        return eventService.getEventsStartingBetweenDates(specificationInput)
                .stream()
                .map(Event::toDto)
                .toList();
    }


    //co gdy dodam kolejny param
    //dedykowanne sposoby na wyszukiwanie po wielu param opcjonalnie
    //class Criteria
    //wyszukiwanie dla Flight


}
