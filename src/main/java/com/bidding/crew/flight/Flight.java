package com.bidding.crew.flight;

import jakarta.persistence.*;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String airportCode;
    private String flightNumber;

    public Flight() {
    }

    public Flight(String flightNumber, String airportCode) {
        this.flightNumber = flightNumber;
        this.airportCode = airportCode;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
