package com.bidding.crew.flight;

import com.bidding.crew.flight.generator.FlightGeneratorFacade;
import com.bidding.crew.flight.generator.Source;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


//Service uzywa encji - sterowany przez kontroler, dostarcza mu DTos,

@Service
public class FlightService {
    private FlightRepository flightRepository;


    public FlightService(FlightRepository flightRepository, FlightGeneratorFacade flightGeneratorFacade) {
        this.flightRepository = flightRepository;
        System.out.println("flight service");
        flightRepository.saveAll(flightGeneratorFacade.generateFlights());
    }

    //todo:poprawic
    public void saveFlight(FlightDto flightDto) {
        flightRepository.save(new Flight(flightDto));
    }

    public List<FlightDto> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        List<FlightDto> flightDtos = new ArrayList<>();
        for (Flight flight : flights) {
            flightDtos.add(flight.toDto());
        }
        return flightDtos;
    }

    public void addFlights(List<FlightDto> flightDtos) {
        List<Flight> flights = new ArrayList<>();
        for (FlightDto flightDto : flightDtos) {
            flights.add(new Flight(flightDto));
        }
        flightRepository.saveAll(flights);
    }
}
