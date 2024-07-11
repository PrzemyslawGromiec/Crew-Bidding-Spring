package com.bidding.crew.report;

public class ReportRequest {

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
