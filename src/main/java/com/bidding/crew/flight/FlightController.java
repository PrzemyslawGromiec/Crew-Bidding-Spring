package com.bidding.crew.flight;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v0/flights")
@Tag(name = "4. Flights",
        description = "Endpoints for managing and querying flights")
@CrossOrigin(origins = "https://crew-bidding-spring-production.up.railway.app")
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> addFlights(@RequestBody List<FlightDto> flightDtos) {
        flightService.addFlights(flightDtos);
        return ResponseEntity.ok("Flights added");
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "Upload a flight file",
            description = "Accepts a multipart/form-data file with flights and stores them"
    )
    public ResponseEntity<?> handleFileUpload(@RequestPart("file") MultipartFile file) throws IOException {
        flightService.flightFileUpload(file);
        return ResponseEntity.ok("File uploaded: " + file.getOriginalFilename());
    }

    @PostMapping("/search")
    @Operation(
            summary = "Search flights by criteria",
            description = "Returns flights matching specific search criteria"
    )
    public List<FlightDto> findFlightsByCriteria(@RequestBody FlightSpecificationInput specificationInput) {
        return flightService.findFlightByCriteria(specificationInput);
    }

}
