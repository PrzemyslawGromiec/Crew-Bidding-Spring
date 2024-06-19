package com.bidding.crew.flight;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

//@Controller // zwraca widoki (html)
@RestController // zwraca dane (json)
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
        System.out.println("flight controller");
    }

    @GetMapping("/api/v0/flights/1")
    public FlightDto getFlight() {
        return new FlightDto("BER", "123", LocalDateTime.MIN,LocalDateTime.MAX, AircraftType.A320);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @PostMapping("/api/v0/flights")
    public void addFlights(@RequestBody List<FlightDto> flightDtos) {
        flightService.addFlights(flightDtos);
    }

   /* @GetMapping("/api/v0/flights")
    public List<FlightDto> getFlights() {
        return flightService.getAllFlights();
    }
*/
    @PostMapping("/api/v0/flights/upload")
    public String handleFileUpload(MultipartFile file) throws IOException {
        flightService.flightFileUpload(file);
        return "File created" + file.getName();
    }

    @GetMapping("/api/v0/flights")
    public List<FlightDto> findFlightsByCriteria(@RequestBody FlightSpecificationInput specificationInput) {
        return flightService.findFlightByCriteria(specificationInput);
    }

}



/*
* Http Request
* Path: https:\\www.google.com?city=warsaw&country=poland
* Parameters ^
* Method: GET/POST/DELETE/PATCH/PUT
* Headers: informacje na temat zapytania
* Body: treść
*
* Http Response
* Headers
* Body
* Status
*
*REST API - backend zorientowany na zasoby
*
* Przykład:
* Path: localhost:8080\hello
* Method: GET
* */


//javascript html css react