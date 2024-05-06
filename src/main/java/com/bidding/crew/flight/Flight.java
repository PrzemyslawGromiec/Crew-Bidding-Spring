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
    private AircraftType aircraftType;


    public Flight() {
    }

    public Flight(FlightDto flightDto) {
        flightNumber = flightDto.getFlightNumber();
        airportCode = flightDto.getAirportCode();
        reportTime = flightDto.getReportTime();
        clearTime = flightDto.getClearTime();
        aircraftType = flightDto.getAircraftType();
    }

    public FlightDto toDto() {
        return new FlightDto(airportCode,flightNumber,reportTime,clearTime,aircraftType);
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

    public AircraftType getAircraftType() {
        return aircraftType;
    }
}
