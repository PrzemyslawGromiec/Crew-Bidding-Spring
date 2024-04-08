package com.bidding.crew.flight;

import org.springframework.web.bind.annotation.*;

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
        return new FlightDto("BER", "123");
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    /*@GetMapping("/api/v0/flights")
    public void addFlight(String airportCode, String flightNumber) {
        System.out.println("airportCode: " + airportCode + " flightNumber: " + flightNumber);
    }*/

    @PostMapping("/api/v0/flights")
    public void addFlight(@RequestBody FlightDto flightDto) {
        flightService.saveFlight(flightDto);
    }

    @GetMapping("/api/v0/flights")
    public List<FlightDto> getFlights() {
        return flightService.getAllFlights();
    }

    //metody do odpowiadania na zapytania do serwera (endpointy)
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
*REST API - backent zorientowany na zasoby
*
* Przykład:
* Path: localhost:8080\hello
* Method: GET
* */


//javascript html css react