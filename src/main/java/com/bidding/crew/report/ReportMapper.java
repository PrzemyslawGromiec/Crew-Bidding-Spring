package com.bidding.crew.report;

import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ReportMapper {
    private FlightService flightService;

    public ReportMapper(FlightService flightService) {
        this.flightService = flightService;
    }

    ReportResponse toResponse(Report report) {
        List<FlightDto> availableFlights = report.generatePeriods().stream()
                .flatMap(periodDto -> flightService.getFlightsForPeriod(periodDto,true).stream())
                .sorted(Comparator.comparing(Flight::getReportTime))
                .map(Flight::toDto)
                .toList();

        return report.toResponse()
                .availableFlights(availableFlights)
                .build();
    }
}
