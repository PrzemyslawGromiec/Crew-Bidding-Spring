package com.bidding.crew.report;

import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;

import java.util.ArrayList;
import java.util.List;

public class FlightRequestFactory {
    private List<FlightRequest> requests = new ArrayList<>();

    public void buildRequest(Flight chosenFlight, int priority) {
        FlightRequest flightRequest = new FlightRequest(chosenFlight, priority);
        requests.add(flightRequest);
    }

    public List<FlightRequest> getRequests() {
        return requests;
    }
}
