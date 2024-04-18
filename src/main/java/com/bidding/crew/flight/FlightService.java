package com.bidding.crew.flight;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//Service uzywa encji - sterowany przez kontroler, dostarcza mu DTos,

@Service
public class FlightService {
    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        System.out.println("flight service");
    }

    //todo:poprawic
    public void saveFlight(FlightDto flightDto) {
        flightRepository.save(new Flight(flightDto.getFlightNumber(), flightDto.getAirportCode(),
                flightDto.getReportTime(),flightDto.getClearTime()));
    }

    public List<FlightDto> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        List<FlightDto> flightDtos = new ArrayList<>();
        for (Flight flight : flights) {
            flightDtos.add(new FlightDto(flight.getFlightNumber(), flight.getAirportCode(),
                    flight.getReportTime(), flight.getClearTime()));
        }
        return flightDtos;
    }

    public void addFlights(List<FlightDto> flightDtos) {
        List<Flight> flights = new ArrayList<>();
        for (FlightDto flightDto : flightDtos) {
            flights.add(new Flight(flightDto.getFlightNumber(),flightDto.getAirportCode(),
                    flightDto.getReportTime(),flightDto.getClearTime()));
        }
        flightRepository.saveAll(flights);
    }
}
