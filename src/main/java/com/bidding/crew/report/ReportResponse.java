package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private Long id;
    private List<ReportEventDto> eventRequest;
    private List<ReportFlightResponse> flightRequests;
    private List<FlightDto> availableFlights;
}

