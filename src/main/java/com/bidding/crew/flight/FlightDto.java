package com.bidding.crew.flight;

import java.time.LocalDateTime;

public class FlightDto {
    private String airportCode;
    private String flightNumber;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;

    public FlightDto() {
    }

    public FlightDto(String airportCode, String flightNumber, LocalDateTime reportTime, LocalDateTime clearTime) {
        this.airportCode = airportCode;
        this.flightNumber = flightNumber;
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

    @Override
    public String toString() {
        return "FlightDto{" +
                "airportCode='" + airportCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                '}';
    }
}


//na potrzeby bibl Jackson potrzebujemy bezparam. konstruktor i getteery do wszystkich pol