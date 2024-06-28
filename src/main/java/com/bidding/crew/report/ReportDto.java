package com.bidding.crew.report;

import java.util.ArrayList;
import java.util.List;

public class ReportDto {
    private Long id;
    private boolean reportFinalized;
    private List<Period> periods = new ArrayList<>();

    public ReportDto() {
    }

    public boolean isReportFinalized() {
        return reportFinalized;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportFinalized(boolean reportFinalized) {
        this.reportFinalized = reportFinalized;
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "id=" + id +
                ", reportState=" + reportFinalized +
                '}';
    }
}
