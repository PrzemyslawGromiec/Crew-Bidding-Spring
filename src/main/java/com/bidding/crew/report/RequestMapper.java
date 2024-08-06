package com.bidding.crew.report;

import com.bidding.crew.flight.FlightRepository;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    private FlightRepository flightRepository;

    public RequestMapper(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public ReportFlight mapToEntity(ReportFlightRequest reportFlightRequest) {
        int id = reportFlightRequest.getFlightId();
        return new ReportFlight(flightRepository.findById(id).orElseThrow(), reportFlightRequest.getNumOfStars());
    }

}
