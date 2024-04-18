package com.bidding.crew.flight;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String airportCode;
    private String flightNumber;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;


    public Flight() {
    }

    public Flight(String flightNumber, String airportCode, LocalDateTime reportTime, LocalDateTime clearTime) {
        this.flightNumber = flightNumber;
        this.airportCode = airportCode;
        this.reportTime = reportTime;
        this.clearTime = clearTime;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public LocalDateTime getClearTime() {
        return clearTime;
    }
}
