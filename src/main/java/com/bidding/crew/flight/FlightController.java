package com.bidding.crew.flight;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(value = "/api/v0/flights", consumes = "application/json")
    public ResponseEntity<?> addFlights(@RequestBody List<FlightDto> flightDtos) {
        flightService.addFlights(flightDtos);
        return ResponseEntity.ok("Flights added");
    }

    @PostMapping(value = "/api/v0/flights", consumes = "multipart/form-data")
    public ResponseEntity<?> handleFileUpload(@RequestPart("file") MultipartFile file) throws IOException {
        flightService.flightFileUpload(file);
        return ResponseEntity.ok("File uploaded: " + file.getOriginalFilename());
    }


    @GetMapping("/api/v0/flights")
    public List<FlightDto> findFlightsByCriteria(FlightSpecificationInput specificationInput) {
        System.out.println("Received spec input:" + specificationInput);
        return flightService.findFlightByCriteria(specificationInput);
    }
}
