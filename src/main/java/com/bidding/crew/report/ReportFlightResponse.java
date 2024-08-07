package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;

public class ReportFlightResponse {

    private FlightDto flightDto;
    private int numOfStars;
    private int points;

    public ReportFlightResponse(FlightDto flightDto, int numOfStars, int points) {
        this.flightDto = flightDto;
        this.numOfStars = numOfStars;
        this.points = points;
    }

    public ReportFlightResponse() {
    }

    public FlightDto getFlightDto() {
        return flightDto;
    }

    public int getNumOfStars() {
        return numOfStars;
    }

    public int getPoints() {
        return points;
    }
}
