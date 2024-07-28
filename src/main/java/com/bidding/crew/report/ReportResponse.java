package com.bidding.crew.report;

import java.util.List;

public class ReportResponse {
    private Long id;
    private boolean closed;
    private List<EventRequestDto> eventRequest;
    private List<PeriodDto> periods;
    private String message;

    public ReportResponse() {
    }

    public ReportResponse(String message) {
        this.message = message;
    }

    public ReportResponse(Long id, boolean closed, List<EventRequestDto> eventRequest, List<PeriodDto> periods) {
        this.id = id;
        this.closed = closed;
        this.eventRequest = eventRequest;
        this.periods = periods;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isClosed() {
        return closed;
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

    public List<PeriodDto> getPeriods() {
        return periods;
    }


    @Override
    public String toString() {
        return "ReportDto{" +
                "id=" + id +
                ", reportState=" + closed +
                '}';
    }
}