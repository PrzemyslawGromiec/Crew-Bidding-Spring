package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;

public class ReportFlightResponse {

    private FlightDto flightDto;
    private int numOfStars;

    public ReportFlightResponse(FlightDto flightDto, int numOfStars) {
        this.flightDto = flightDto;
        this.numOfStars = numOfStars;
    }

    public ReportFlightResponse() {
    }

    public FlightDto getFlightDto() {
        return flightDto;
    }

    public int getNumOfStars() {
        return numOfStars;
    }
}
