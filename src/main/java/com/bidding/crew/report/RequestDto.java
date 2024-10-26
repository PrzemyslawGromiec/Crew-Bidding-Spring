package com.bidding.crew.report;

import lombok.Data;

import java.time.LocalDateTime;

enum RequestType {
    FLIGHT, EVENT;
}

public record RequestDto(int id, int stars, RequestType type, LocalDateTime startTime, LocalDateTime endTime) {
}
