package com.bidding.crew.report;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {
    private Long id;
    private boolean closed;
    private List<ReportEventDto> eventRequest;
    private List<PeriodDto> periods;
    private List<ReportFlightResponse> flightRequests;

    public ReportResponse() {
    }

    public ReportResponse(Long id, boolean closed, List<ReportEventDto> eventRequest, List<PeriodDto> periods, List<ReportFlightResponse> flightRequests) {
        this.id = id;
        this.closed = closed;
        this.eventRequest = eventRequest;
        this.periods = periods;
        this.flightRequests = flightRequests;
    }

    public boolean isClosed() {
        return closed;
    }

    public Long getId() {
        return id;
    }

    public List<ReportEventDto> getEventRequest() {
        return eventRequest;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PeriodDto> getPeriods() {
        return periods;
    }

    public List<ReportFlightResponse> getFlightRequests() {
        return flightRequests;
    }

    public void setFlightRequests(List<ReportFlightResponse> flightRequests) {
        this.flightRequests = flightRequests;
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "id=" + id +
                ", reportState=" + closed +
                '}';
    }
}
