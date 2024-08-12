package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v0/reports")
public class ReportController {
    private final FlightService flightService;
    private ReportService reportService;

    public ReportController(ReportService reportService, FlightService flightService) {
        this.reportService = reportService;
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@Valid @RequestBody ReportRequest reportRequest) {
        ReportResponse reportResponse = reportService.createReport(reportRequest);
        return ResponseEntity.ok(reportResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Long id) {
        ReportResponse reportResponse = reportService.getReport(id);
        return ResponseEntity.ok(reportResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateStatus(@PathVariable Long id, @RequestBody ReportRequest reportRequest) {
        ReportResponse updatedReport = reportService.updateStatus(id, reportRequest);
        return ResponseEntity.ok(updatedReport);
    }

    @GetMapping("{id}/periods")
    public ResponseEntity<List<PeriodDto>> getPeriods(@PathVariable Long id) {
        List<PeriodDto> periods = reportService.getAllPeriods(id);
        return ResponseEntity.ok(periods);
    }

    @GetMapping("/{id}/generatedPeriods")
    public ResponseEntity<List<PeriodDto>> getGeneratedPeriods(@PathVariable Long id) {
        try {
            List<PeriodDto> periods = reportService.generatePeriodsForReport(id);
            return ResponseEntity.ok(periods);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/suggestions")
    public ResponseEntity<List<FlightDto>> getSuggestionsForPeriod(@PathVariable Long id, @RequestBody SuggestionCriteriaDto criteria) {
        List<FlightDto> suggestedFlights = reportService.getSuggestedFlightsForPeriods(id, criteria);
        return ResponseEntity.ok(suggestedFlights);
    }

    @PostMapping("{id}/flights")
    public ResponseEntity<ReportFlightResponse> addFlightToReport(@PathVariable Long id, @RequestBody ReportFlightRequest flight) {
        ReportFlightResponse flightDto = reportService.saveFlight(id, flight);
        return ResponseEntity.ok(flightDto);
    }

}
