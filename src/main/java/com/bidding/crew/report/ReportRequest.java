package com.bidding.crew.report;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReportRequest {

    @NotNull(message = "Id cannot be null")
    private Long id;
    private List<RequestDto> requests;

    public ReportRequest() {
    }

    public Long getId() {
        return id;
    }

    public List<RequestDto> getRequests() {
        return requests;
    }
}
