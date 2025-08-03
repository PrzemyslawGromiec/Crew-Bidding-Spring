package com.bidding.crew.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/events")
@Tag(name = "3. Events",
        description = "Endpoints for managing and querying custom events")

public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping()
    public void saveEvent(@RequestBody EventDto eventDto) {
        eventService.saveEvent(eventDto);
    }

    @GetMapping()
    @Operation(
            summary = "Find events by criteria",
            description = "Returns a list of events matching the specified filter criteria"
    )
    public List<EventDto> findEventsByCriteria(@RequestBody SpecificationInput specificationInput) {
        System.out.println("Received SpecificationInput: " + specificationInput);
        return eventService.findEventsByCriteria(specificationInput);
    }
}
