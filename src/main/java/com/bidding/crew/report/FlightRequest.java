package com.bidding.crew.report;

import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;

import java.time.LocalDateTime;

public class FlightRequest extends Request {
    private FlightDto flightDto;
    private int numOfStars;

    public FlightRequest(FlightDto flightDto, int numOfStars) {
        this.flightDto = flightDto;
        this.numOfStars = numOfStars;
    }

    @Override
    public LocalDateTime startTime() {
        return null;
    }

    @Override
    public LocalDateTime endTime() {
        return null;
    }

    public FlightDto getFlightDto() {
        return flightDto;
    }

    public int getNumOfStars() {
        return numOfStars;
    }
}
