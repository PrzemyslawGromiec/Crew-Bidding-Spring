package com.bidding.crew;

import com.bidding.crew.flight.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")

public class FlightServiceTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();

        FlightDto flightDto1 = new FlightDto(0, "CDG", "BA789",
                LocalDateTime.of(2025, 3, 7, 9, 0),
                LocalDateTime.of(2025, 3, 7, 11, 0),
                AircraftType.A320);
        Flight flight1 = new Flight(flightDto1);

        FlightDto flightDto2 = new FlightDto(0, "", "BA123",
                LocalDateTime.of(2025, 3, 7, 9, 0),
                LocalDateTime.of(2025, 3, 7, 11, 0),
                AircraftType.A319);
        Flight flight2 = new Flight(flightDto2);

        flightRepository.saveAll(List.of(flight1,flight2));
    }

    @Test
    void shouldRetrieveFlightsByAirport() {
        // When
        List<FlightDto> flights = flightService.getFlightsByAirport("CDG");

        // Then
        assertFalse(flights.isEmpty(), "Flights should not be empty");
        assertEquals(1, flights.size(), "Should return 1 flight");
        assertEquals("", flights.getFirst().getAirportCode(), "Airport code should match");
    }

    @Test
    void shouldRetrieveAllFlights() {
        // When
        List<FlightDto> flights = flightService.getAllFlights();

        // Then
        assertEquals(2, flights.size(), "Should return exactly 2 flights");
    }

}
