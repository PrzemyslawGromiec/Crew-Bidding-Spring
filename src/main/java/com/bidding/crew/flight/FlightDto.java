package com.bidding.crew.flight;

public class FlightDto {
    private String airportCode;
    private String flightNumber;

    public FlightDto() {
    }

    public FlightDto(String airportCode, String flightNumber) {
        this.airportCode = airportCode;
        this.flightNumber = flightNumber;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getFlightNumber() {
        return flightNumber;
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