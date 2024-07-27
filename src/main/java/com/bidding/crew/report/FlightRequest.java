package com.bidding.crew.report;

import com.bidding.crew.flight.Flight;

import java.time.LocalDateTime;

public class FlightRequest extends Request {
    private Flight flight;
    private int numOfStars;

    public FlightRequest(Flight flight, int numOfStars) {
        this.flight = flight;
        this.numOfStars = numOfStars;
    }

    @Override
    public LocalDateTime startTime() {
        return flight.getReportTime();
    }

    @Override
    public LocalDateTime endTime() {
        return flight.getClearTime();
    }

    @Override
    public LocalDateTime endTimeBuffered() {
        return flight.getClearTimeWithBuffer();
    }

    public int getNumOfStars() {
        return numOfStars;
    }

    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return "FlightRequest{" +
                "flight=" + flight +
                ", numOfStars=" + numOfStars +
                '}';
    }
}
