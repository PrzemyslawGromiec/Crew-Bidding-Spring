package com.bidding.crew.report;

import lombok.Getter;

@Getter
public class ReportFlightRequest {
    private int flightId;
    private int numOfStars;

    public ReportFlightRequest(int flightId, int numOfStars) {
        if (flightId <= 0) {
            throw new IllegalArgumentException("Flight ID must be a positive integer.");
        }
        if (numOfStars < 1 || numOfStars > 3) {
            throw new IllegalArgumentException("Number of stars must be between 1 and 3.");
        }
        this.flightId = flightId;
        this.numOfStars = numOfStars;
    }

    public ReportFlightRequest() {
    }

}
