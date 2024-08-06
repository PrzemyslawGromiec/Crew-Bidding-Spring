package com.bidding.crew.flight;

import jakarta.persistence.*;

import java.time.Duration;
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
        return new FlightDto(id,airportCode, flightNumber, reportTime, clearTime, aircraftType);
    }


    public Duration getFlightDuration() {
        return Duration.between(reportTime, clearTime);
    }

    public int getId() {
        return id;
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

    public LocalDateTime getClearTimeWithBuffer() {
        return getClearTime().plus(calculateBuffer());
    }

    public Duration calculateBuffer() {
        if (getFlightDuration().compareTo(Duration.ofHours(14)) < 0) {
            return Duration.ofHours(12).plusMinutes(30);
        } else {
            return Duration.ofHours(48);
        }
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airportCode='" + airportCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", reportTime=" + reportTime +
                ", clearTime=" + clearTime +
                ", aircraftType=" + aircraftType +
                '}';
    }
}




