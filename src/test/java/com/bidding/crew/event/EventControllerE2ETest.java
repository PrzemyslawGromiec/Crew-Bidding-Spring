package com.bidding.crew.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EventControllerE2ETest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testGetEventsEndpoint() {
        eventRepository.deleteAll();
        Event event = new Event(
                LocalDateTime.of(2024, 4, 20, 1, 1),
                LocalDateTime.of(2024, 4, 22, 10, 2),
                1, "first", false);
        eventRepository.save(event);

        ResponseEntity<List<EventDto>> response = restTemplate.exchange(
                "/api/v0/events", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertEquals(200, response.getStatusCode().value());
        List<EventDto> dtos = response.getBody();
        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(1, dtos.size());


    }
}
