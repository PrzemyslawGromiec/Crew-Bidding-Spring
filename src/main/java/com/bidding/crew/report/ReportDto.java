package com.bidding.crew.report;

import java.util.ArrayList;
import java.util.List;

public class ReportDto {
    private Long id;
    private boolean reportFinalized;
    private List<EventRequestDto> eventRequest;
    private List<PeriodDto> periods;

    public ReportDto() {
    }

    public ReportDto(Long id, boolean reportFinalized, List<EventRequestDto> eventRequest, List<PeriodDto> periods) {
        this.id = id;
        this.reportFinalized = reportFinalized;
        this.eventRequest = eventRequest;
        this.periods = periods;
    }

    public boolean isReportFinalized() {
        return reportFinalized;
    }

    public Long getId() {
        return id;
    }

    public List<EventRequestDto> getEventRequest() {
        return eventRequest;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportFinalized(boolean reportFinalized) {
        this.reportFinalized = reportFinalized;
    }

    public List<PeriodDto> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "id=" + id +
                ", reportState=" + reportFinalized +
                '}';
    }
}
