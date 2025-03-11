package com.bidding.crew.general;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final Map<String, String> errors;

    public ErrorResponse(int status, String message, LocalDateTime timestamp, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

}
