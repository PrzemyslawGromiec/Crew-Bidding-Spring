package com.bidding.crew.demo;

import com.bidding.crew.event.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/frontend")
@CrossOrigin
public class DemoController {

    @PostMapping("/events")
    public ResponseEntity<EventDto> addEvent(@RequestBody EventDto eventDto) {
        EventDto event = new EventDto(LocalDateTime.MIN, LocalDateTime.MAX, 1, eventDto.getDescription(), false);
        System.out.println("test ok");
        return ResponseEntity.ok(event);
    }

}
