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

    @GetMapping("/api/v0/events")
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/api/v0/ev")
    public List<EventDto> getReoccurringEvents(boolean reoccurring) {
        return eventService.getReoccurringEvent(reoccurring);
    }

    @GetMapping("/api/v0/events/before")
    public List<EventDto> getEventsBeforeDate(@RequestParam("startDate") LocalDateTime startDate) {
        return eventService.getEventsStartingBefore(startDate);
    }

    @GetMapping("/api/v0/events/descriptions")
    public List<String> getEventsDescriptionsByPriority(@RequestParam("priority") int priority) {
        return eventService.getEventsDescriptionByPriority(priority);
    }


}
