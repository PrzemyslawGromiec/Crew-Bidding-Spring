package com.bidding.crew.report;

import jakarta.validation.constraints.NotNull;

public class ReportRequest {

    @NotNull(message = "Id cannot be null")
    private Long id;
    private boolean closed;

    public ReportRequest() {
    }

    public Long getId() {
        return id;
    }

    public boolean isClosed() {
        return closed;
    }
}
