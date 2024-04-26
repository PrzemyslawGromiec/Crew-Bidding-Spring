package com.bidding.crew.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest {
    private EventService eventService;

    @Autowired
    public EventServiceTest(EventService eventService) {
        this.eventService = eventService;
    }

    @BeforeEach
    public void initDB() {
        eventService.saveEvent(new EventDto(LocalDateTime.of(2024, 4, 20, 1, 1),
                LocalDateTime.of(2024, 4, 22, 10, 2),
                1, "first", false));
        eventService.saveEvent(new EventDto(LocalDateTime.of(2024, 4, 23, 1, 1),
                LocalDateTime.of(2024, 4, 25, 10, 2),
                1, "first", false));
        eventService.saveEvent(new EventDto(LocalDateTime.of(2024, 4, 24, 1, 1),
                LocalDateTime.of(2024, 4, 26, 10, 2),
                1, "first", true));
    }

    @Test
    public void testFindEvents() {
        List<EventDto> events = eventService.findEventsByParameters(null,
                (LocalDateTime.of(2024, 4, 30, 23, 59)), true);

        Assertions.assertEquals(1, events.size());

        List<EventDto> eventsWhereEndNull = eventService.findEventsByParameters(LocalDateTime.of(2024, 4, 22, 23, 59),
                null,null);

        Assertions.assertEquals(2,eventsWhereEndNull.size());

        List<EventDto> eventsWithNull = eventService.findEventsByParameters(null,null,null);
        Assertions.assertEquals(3,eventsWithNull.size());


    }

}