package com.bidding.crew.flight;

import com.bidding.crew.flight.generator.FlightGeneratorFacade;
import com.bidding.crew.report.Period;
import com.bidding.crew.report.SuggestionCriteriaDto;
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

@Service
public class FlightService {

    private static final int MIN_FLIGHT_HOURS = 0;
    private static final int MAX_FLIGHT_HOURS = Integer.MAX_VALUE;
    private static final List<AircraftType> TYPES = new ArrayList<>(List.of(AircraftType.A319,AircraftType.A320,AircraftType.A321,
            AircraftType.B777,AircraftType.B787));

    private FlightRepository flightRepository;
    private FlightGeneratorFacade flightGeneratorFacade;
    private FlightSpecificationBuilderImpl flightSpecificationBuilder;

    public FlightService(FlightRepository flightRepository, FlightGeneratorFacade flightGeneratorFacade, FlightSpecificationBuilderImpl flightSpecificationBuilderImpl) {
        this.flightRepository = flightRepository;
        this.flightGeneratorFacade = flightGeneratorFacade;
        this.flightSpecificationBuilder = flightSpecificationBuilderImpl;
    }

    public void flightFileUpload(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            System.out.println("File is empty.");
            return;
        }

        String uploadDir = "flights";
        Path uploadPath = Paths.get(System.getProperty("user.home"), uploadDir);
        System.out.println(uploadPath);

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
                .map(Flight::toDto)
                .toList();
    }

    public List<Flight> getFlightsForPeriod(Period period, boolean allDurations) {
        return flightRepository.findAll().stream()
                .filter(flight -> allDurations || preferredDuration(flight))
                .filter(flight -> containsAircraftType(flight.getAircraftType()))
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .collect(Collectors.toList());
    }

    public List<Flight> getFlightsWithinPeriodWithMinDuration(SuggestionCriteriaDto criteria) {
        List<Flight> flights = flightRepository.findFlightsWithinPeriodExcludingOneType(criteria);
        return flights.stream()
                .filter(flight -> flight.getFlightDuration().compareTo(criteria.getMinDuration()) >= 0)
                .toList();
    }

    /*public List<FlightDto> getSuggestedFlightsWithSpecification(SuggestionCriteriaDto criteria) {
       Specification<Flight> specification = flightSpecificationBuilder.getSuggestedFlightsWithSpecification(criteria);
       return flightRepository.findAll(specification)
               .stream()
               .map(Flight::toDto)
               .toList();
    }*/

    private boolean preferredDuration( Flight flight) {
        return flight.getFlightDuration().toHours() >= MIN_FLIGHT_HOURS
                && flight.getFlightDuration().toHours() <= MAX_FLIGHT_HOURS;
    }

    public Flight getFlightById(int id) {
        return flightRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Flight not found with id: " + id));
    }

    public List<Flight> getFlightsById(List<Integer> ids) {
        return flightRepository.findAllById(ids);
    }


    public boolean containsAircraftType(AircraftType type){
        return TYPES.contains(type);
    }

}
