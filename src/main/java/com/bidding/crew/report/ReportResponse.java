package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@ToString
public class ReportResponse {
    private Long id;
    private boolean closed;
    private List<ReportEventDto> eventRequest;
    private List<ReportFlightResponse> flightRequests;
    private List<FlightDto> availableFlights;
}

