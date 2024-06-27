package com.bidding.crew.report;

import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    ReportDto applyAction(ActionType actionType){
        if (reportRepository.count() !=0 && !reportRepository.findAll().getFirst().isReportFinalized()) {
            throw new IllegalStateException("Not finalized report exists.");
        }
        Report report = new Report();
        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportRepository.save(report);
        return reportDto;
    }




}
