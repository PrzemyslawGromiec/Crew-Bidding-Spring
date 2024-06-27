package com.bidding.crew.report;

public class ReportDto {
    private Long id;
    private boolean reportState;

    public ReportDto() {
    }

    public boolean isReportState() {
        return reportState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportState(boolean reportState) {
        this.reportState = reportState;
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "id=" + id +
                ", reportState=" + reportState +
                '}';
    }
}
