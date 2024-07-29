package com.bidding.crew.flight;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/api/v0/flights")
    public void addFlights(@RequestBody List<FlightDto> flightDtos) {
        flightService.addFlights(flightDtos);
    }

    @PostMapping("/api/v0/flights/upload")
    public String handleFileUpload(MultipartFile file) throws IOException {
        flightService.flightFileUpload(file);
        return "File created" + file.getName();
    }

    @GetMapping("/api/v0/flights")
    public List<FlightDto> findFlightsByCriteria(@RequestBody FlightSpecificationInput specificationInput) {
        System.out.println("Received spec input:" + specificationInput);
        return flightService.findFlightByCriteria(specificationInput);
    }

   @GetMapping("/api/v0/flightsByCode")
    public List<FlightDto> getFlightsByAirportCode(@RequestParam String airport) {
        return flightService.getFlightsByAirport(airport);
    }

}
