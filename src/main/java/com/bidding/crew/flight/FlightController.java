package com.bidding.crew.flight;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController // zwraca dane (json)
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
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
        System.out.println("Received spec input:" + specificationInput);
        return flightService.findFlightByCriteria(specificationInput);
    }


  /*  @GetMapping("/api/v0/flights")
    public List<FlightDto> getFlightsByAirportCode(@RequestParam String airport) {
        return flightService.getFlightsByAirport(airport);
    }*/

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