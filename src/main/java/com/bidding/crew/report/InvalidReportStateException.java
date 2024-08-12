package com.bidding.crew.report;

public class InvalidReportStateException extends RuntimeException {
    public InvalidReportStateException(String message) {
        super(message);
    }

    public InvalidReportStateException(String message, Throwable cause) {
        super(message,cause);
    }
}
