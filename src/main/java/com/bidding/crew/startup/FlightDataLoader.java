package com.bidding.crew.startup;

import com.bidding.crew.flight.FlightService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FlightDataLoader {

    private final FlightService flightService;

    public FlightDataLoader(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostConstruct
    public void loadFlightsOnStartup() {
        ClassPathResource resource = new ClassPathResource("Flights.txt");
        try (InputStream inputStream = resource.getInputStream()) {
            MultipartFile file = new MockMultipartFile(
                    "file",
                    resource.getFilename(),
                    "text/plain",
                    inputStream
            );

            flightService.flightFileUpload(file);
            System.out.println("Flights have been uploaded successfully.");
        } catch (IOException e) {
            System.err.println("Failed to upload flights: " + e.getMessage());
        }
    }
}
