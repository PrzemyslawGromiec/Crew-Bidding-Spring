package com.bidding.crew.report;

import com.bidding.crew.flight.FlightDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v0/reports")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportRequest reportRequest) {
        try {
            ReportResponse reportResponse = reportService.createReport(reportRequest);
            return ResponseEntity.ok(reportResponse);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //100 info, 200 ok, 300 redirect
    //400 front-client 500 back-server


    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Long id) {
        try {
            ReportResponse reportResponse = reportService.getReport(id);
            return ResponseEntity.ok(reportResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReportResponse(e.getMessage()));
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
    public ResponseEntity<List<FlightDto>> getSuggestionsForPeriod(@PathVariable Long id, @RequestBody PeriodDto period,
                                                                   @RequestParam(required = false) Long minDurationHours) {
        try {
            Duration minDuration = minDurationHours != null ? Duration.ofHours(minDurationHours) : Duration.ZERO;
            List<FlightDto> suggestedFlights = reportService.getSuggestedFlightsForPeriods(id,period,minDuration);
            return ResponseEntity.ok(suggestedFlights);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

    //post reports -> tworze pusty raport
    //put reports/id  (status=finalized)
    //get reports/id/periods
    //get reports/id/periods/id/sugestions


    //get reports/id/periods/sugestions?sugested=true  -> usprawnienie zeby aplikacja sugerowala ktory kolejny

    //post reports/id/requests -> zaktualizuje periody, doda flight


    //put reports/id/periods/id -> status=closed -> zamyka period



