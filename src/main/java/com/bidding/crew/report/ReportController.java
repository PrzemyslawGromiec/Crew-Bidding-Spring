package com.bidding.crew.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //todo: rzuci status 500 dla nieistniejacego id i poprawic na ResponseEntity 404
    //100 info, 200 ok, 300 redirect
    //400 front-client 500 back-server
    @GetMapping("/{id}")
    public ReportResponse getReport(@PathVariable Long id) {
        return reportService.getReport(id);
    }

    @PutMapping("/{id}")
    public ReportResponse updateStatus(@PathVariable Long id, @RequestBody ReportRequest reportRequest) {
        return reportService.updateStatus(id,reportRequest);
    }



    //post reports -> tworze pusty raport

    //get reports/id/periods/id/sugestions
    //get reports/id/periods/sugestions?sugested=true  -> usprawnienie zeby aplikacja sugerowala ktory kolejny

    //post reports/id/requests -> zaktualizuje periody, doda flight

    //get reports/id/periods

    //put reports/id  (status=finalized)


    //put reports/id/periods/id -> status=closed -> zamyka period


}
