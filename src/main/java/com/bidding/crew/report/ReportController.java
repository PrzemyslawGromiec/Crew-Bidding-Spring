package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v0/reports")
@Tag(name = "Report")
@CrossOrigin(origins = "http://localhost:8086")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService, FlightService flightService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Long id) {
        ReportResponse reportResponse = reportService.getReport(id);
        return ResponseEntity.ok(reportResponse);
    }

    @PostMapping
    @Operation(
            method = "Finalize report",
            description = "Update report status",
            responses = @ApiResponse(
                    description = "Report finalized successfully",
                    responseCode = "200"
            )
    )
    public ResponseEntity<ReportResponse> finalizeReport(@RequestBody ReportRequest reportRequest) {
        ReportResponse updatedReport = reportService.finalizeReport(reportRequest);
        return ResponseEntity.ok(updatedReport);
    }

    @PostMapping("/created")
    public ResponseEntity<ReportResponse> createReport() {
        ReportResponse reportResponse = reportService.createReport();
        return ResponseEntity.ok(reportResponse);
    }

    @GetMapping("/{id}/suggestions")
    public ResponseEntity<List<FlightDto>> getSuggestionsForPeriod(@PathVariable Long id, @RequestBody SuggestionCriteriaDto criteria) {
        List<FlightDto> suggestedFlights = reportService.getSuggestedFlightsForPeriods(id, criteria);
        return ResponseEntity.ok(suggestedFlights);
    }

    @PostMapping("{id}/flights")
    public ResponseEntity<ReportResponse> addFlightToReport(@PathVariable Long id, @RequestBody ReportFlightRequest flight) {
        ReportResponse reportResponse = reportService.saveFlight(id, flight);
        return ResponseEntity.ok(reportResponse);
    }

    @GetMapping("{id}/periods")
    public ResponseEntity<List<Period>> getPeriods(@PathVariable Long id) {
        List<Period> periods = reportService.getAllPeriods(id);
        return ResponseEntity.ok(periods);
    }

    @GetMapping("/{id}/generatedPeriods")
    public ResponseEntity<List<Period>> getGeneratedPeriods(@PathVariable Long id) {
        try {
            List<Period> periods = reportService.generatePeriodsForReport(id);
            return ResponseEntity.ok(periods);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("{id}/events")
    public ResponseEntity<ReportResponse> addEventToReport(@PathVariable Long id, @RequestBody ReportEventDto event) {
        ReportResponse reportResponse = reportService.saveEvent(id, event);
        return ResponseEntity.ok(reportResponse);
    }

}
