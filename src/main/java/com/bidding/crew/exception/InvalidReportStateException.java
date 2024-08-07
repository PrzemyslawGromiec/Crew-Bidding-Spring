package com.bidding.crew.exception;

public class InvalidReportStateException extends RuntimeException {
    public InvalidReportStateException(String message) {
        super(message);
    }

    public InvalidReportStateException(String message, Throwable cause) {
        super(message,cause);
    }
}
