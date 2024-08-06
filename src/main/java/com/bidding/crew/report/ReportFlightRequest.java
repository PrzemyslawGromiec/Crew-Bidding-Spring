package com.bidding.crew.report;

public class ReportFlightRequest {
    private int flightId;
    private int numOfStars;

    public ReportFlightRequest(int flightId, int numOfStars) {
        this.flightId = flightId;
        this.numOfStars = numOfStars;
    }

    public ReportFlightRequest() {
    }

    public int getFlightId() {
        return flightId;
    }

    public int getNumOfStars() {
        return numOfStars;
    }
}
