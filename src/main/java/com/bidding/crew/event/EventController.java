package com.bidding.crew.event;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("api/v0/events")
    public List<EventDto> findEventsByCriteria(@RequestBody SpecificationInput specificationInput) {
        return eventService.findEventsByCriteria(specificationInput);
    }

}
