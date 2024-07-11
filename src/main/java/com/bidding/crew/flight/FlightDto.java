package com.bidding.crew.flight;

import java.time.LocalDateTime;

public class FlightDto {
    private int id;
    private String airportCode;
    private String flightNumber;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private AircraftType aircraftType;

    public FlightDto() {
    }

    public FlightDto(String airportCode, String flightNumber, LocalDateTime reportTime, LocalDateTime clearTime, AircraftType aircraftType) {
        this.airportCode = airportCode;
        this.flightNumber = flightNumber;
        this.reportTime = reportTime;
        this.clearTime = clearTime;
        this.aircraftType = aircraftType;
    }

    public FlightDto(int id, String airportCode, String flightNumber, LocalDateTime clearTime, LocalDateTime reportTime, AircraftType aircraftType) {
        this.id = id;
        this.airportCode = airportCode;
        this.flightNumber = flightNumber;
        this.clearTime = clearTime;
        this.reportTime = reportTime;
        this.aircraftType = aircraftType;
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

/*    @Override
    public String toString() {
        return "FlightDto{" +
                "airportCode='" + airportCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", reportTime=" + reportTime +
                ", clearTime=" + clearTime +
                ", aircraftType=" + aircraftType +
                '}';
    }*/

    @Override
    public String toString() {
        return "FlightDto{" +
                "id=" + id +
                ", airportCode='" + airportCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", reportTime=" + reportTime +
                ", clearTime=" + clearTime +
                ", aircraftType=" + aircraftType +
                '}';
    }
}
