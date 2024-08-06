package com.bidding.crew.report;

import com.bidding.crew.flight.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightRequestFactory {
    private List<ReportFlight> requests = new ArrayList<>();

    public void buildRequest(Flight chosenFlight, int priority) {
        ReportFlight reportFlight = new ReportFlight(chosenFlight, priority);
        requests.add(reportFlight);
    }

    public List<ReportFlight> getRequests() {
        return requests;
    }
}
