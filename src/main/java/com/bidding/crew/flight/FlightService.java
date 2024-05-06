package com.bidding.crew.flight;

import com.bidding.crew.flight.generator.FlightGeneratorFacade;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//Service uzywa encji - sterowany przez kontroler, dostarcza mu DTos,

@Service
public class FlightService {
    private FlightRepository flightRepository;
    private FlightGeneratorFacade flightGeneratorFacade;


    public FlightService(FlightRepository flightRepository, FlightGeneratorFacade flightGeneratorFacade) {
        this.flightRepository = flightRepository;
        System.out.println("flight service");
      //  flightRepository.saveAll(flightGeneratorFacade.generateFlights());
        this.flightGeneratorFacade = flightGeneratorFacade;
    }


    //todo: poczytac o tym MultipartFile ktory ma sluzyc do zaladowania danych z pliku przez postmana!
    public void flightFileUpload(MultipartFile multipartFile) throws IOException {
        String path = "tempdest.txt";
        File file = new File(path);
        multipartFile.transferTo(file);
        flightRepository.saveAll(flightGeneratorFacade.generateFlights(path));
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
