package com.bidding.crew.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/api/v0/reportActions")
    public ResponseEntity<?> sendAction(@RequestBody ReportActionDto reportActionDto){
        System.out.println(reportActionDto);
        try {
            ReportDto reportDto = reportService.applyAction(reportActionDto.getActionType());
            return ResponseEntity.ok(reportDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
