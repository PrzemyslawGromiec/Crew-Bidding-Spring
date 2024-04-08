package com.bidding.crew.flight;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FlightService {
    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        System.out.println("flight service");
    }

    public void saveFlight(FlightDto flightDto) {
        flightRepository.save(new Flight(flightDto.getFlightNumber(), flightDto.getAirportCode()));
    }

    public List<FlightDto> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        List<FlightDto> flightDtos = new ArrayList<>();
        for (Flight flight : flights) {
            flightDtos.add(new FlightDto(flight.getFlightNumber(), flight.getAirportCode()));
        }
        return flightDtos;
    }
}
