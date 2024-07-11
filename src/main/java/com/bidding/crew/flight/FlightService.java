package com.bidding.crew.flight;

import com.bidding.crew.flight.generator.FlightGeneratorFacade;
import com.bidding.crew.general.Preference;
import com.bidding.crew.report.Period;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//Service uzywa encji - sterowany przez kontroler, dostarcza mu DTos,

@Service
public class FlightService {
    private FlightRepository flightRepository;
    private FlightGeneratorFacade flightGeneratorFacade;
    private FlightSpecificationBuilderImpl flightSpecificationBuilder;
    private Preference preference;


    public FlightService(FlightRepository flightRepository, FlightGeneratorFacade flightGeneratorFacade, FlightSpecificationBuilderImpl flightSpecificationBuilderImpl,
                         Preference preference) {
        this.flightRepository = flightRepository;
        System.out.println("flight service");
        //  flightRepository.saveAll(flightGeneratorFacade.generateFlights());
        this.flightGeneratorFacade = flightGeneratorFacade;
        this.flightSpecificationBuilder = flightSpecificationBuilderImpl;
        this.preference = preference;
    }


    public void flightFileUpload(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            System.out.println("File is empty.");
            return;
        }

        String uploadDir = "C:\\Users\\pgrom\\OneDrive\\Desktop\\JAVA_all_projects\\Bidding-Crew";
        ///path/to/upload/dir
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = multipartFile.getOriginalFilename();
        if (fileName != null) {
            fileName = Paths.get(fileName).getFileName().toString();
        } else {
            throw new IOException("File name is invalid.");
        }

        Path filePath = uploadPath.resolve(fileName);
        File file = filePath.toFile();
        multipartFile.transferTo(file);
        flightRepository.saveAll(flightGeneratorFacade.generateFlights(file.getPath()));
    }

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

    public List<FlightDto> getFlightsByAirport(String airportCode) {
        List<Flight> flights = flightRepository.findFlightsByAirport(airportCode);
        List<FlightDto> flightDtos = new ArrayList<>();
        for (Flight flight : flights) {
            flightDtos.add(flight.toDto());
        }

        return flightDtos;

    }

    public List<FlightDto> findFlightByCriteria(FlightSpecificationInput input) {
        Specification<Flight> specification = flightSpecificationBuilder.getSpecificationFor(input);

        return flightRepository.findAll(specification)
                .stream()
                .map(Flight::toDtoWithId)
                .toList();
    }

    public List<Flight> getFlightsForPeriod(Period period, boolean allDurations) {
        return flightRepository.findAll().stream()
                .filter(flight -> allDurations || preferredDuration(preference, flight))
                .filter(flight -> preference.containsAircraftType(flight.getAircraftType()))
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .collect(Collectors.toList());
    }

    private  boolean preferredDuration(Preference preference, Flight flight) {
        return flight.getFlightDuration().toHours() >= preference.getMinFlightHours()
                && flight.getFlightDuration().toHours() <= preference.getMaxFlightHours();
    }

    private Flight getFlightById(int id) {
        return flightRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Flight not found with id: " + id));
    }

}
