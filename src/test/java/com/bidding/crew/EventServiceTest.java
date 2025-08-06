package com.bidding.crew;

import com.bidding.crew.event.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventSpecificationBuilder eventSpecificationBuilder;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testSaveEvent() {
        //arrange
        EventDto eventDto = new EventDto(LocalDateTime.of(2024, 4, 20, 1, 1),
                LocalDateTime.of(2024, 4, 22, 10, 2),
                1, "first", false);

        //act
        eventService.saveEvent(eventDto);

        //assert
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    public void testGetEventsAndGetEventsDtos() {
        Event event = new Event(LocalDateTime.of(2024, 4, 20, 1, 1),
                LocalDateTime.of(2024, 4, 22, 10, 2),
                1, "first", false);

        when(eventRepository.findAll()).thenReturn(List.of(event));

        List<Event> events = eventService.getEvents();
        Assertions.assertEquals(1, events.size());

        List<EventDto> dtos = eventService.getEventDtos();
        Assertions.assertEquals(1, dtos.size());
    }

    @Test
    public void testGetEventsAndGetEventDtosBDDStyle() {
        //BDD (Behavior-Driven Development) test
        Event event = new Event(LocalDateTime.of(2024, 4, 20, 1, 1),
                LocalDateTime.of(2024, 4, 22, 10, 2),
                1, "first", false);

        given(eventRepository.findAll()).willReturn(List.of(event));

        List<Event> events = eventService.getEvents();
        then(events).hasSize(1);

        List<EventDto> dtos = eventService.getEventDtos();
        then(dtos).hasSize(1);

        org.mockito.BDDMockito.then(eventRepository).should(Mockito.times(2)).findAll();

    }


}