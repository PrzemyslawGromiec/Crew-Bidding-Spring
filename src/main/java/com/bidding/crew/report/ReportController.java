package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<ReportResponse> createReport(@RequestBody ReportRequest reportRequest) {
        ReportResponse reportResponse = reportService.createReport(reportRequest);
        return ResponseEntity.ok(reportResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Long id) {
        try {
            ReportResponse reportResponse = reportService.getReport(id);
            return ResponseEntity.ok(reportResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReportResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ReportResponse());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateStatus(@PathVariable Long id, @RequestBody ReportRequest reportRequest) {
        try {
            ReportResponse updatedReport = reportService.updateStatus(id, reportRequest);
            return ResponseEntity.ok(updatedReport);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReportResponse());
        }
    }

    @GetMapping("{id}/periods")
    public ResponseEntity<List<PeriodDto>> getPeriods(@PathVariable Long id) {
        try {
            List<PeriodDto> periods = reportService.getAllPeriods(id);
            return ResponseEntity.ok(periods);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new PeriodDto()));
        }
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
        try {
            List<FlightDto> suggestedFlights = reportService.getSuggestedFlightsForPeriods(id, criteria);
            return ResponseEntity.ok(suggestedFlights);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("{id}/flights")
    public ResponseEntity<ReportFlightResponse> addFlightToReport(@PathVariable Long id, @RequestBody ReportFlightRequest flight) {
        ReportFlightResponse flightDto = reportService.saveFlight(id, flight);
        return ResponseEntity.ok(flightDto);
    }

    //todo: globalny exception handler
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), LocalDateTime.now()));
    }
}
