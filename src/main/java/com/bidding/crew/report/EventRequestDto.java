package com.bidding.crew.report;

import java.time.LocalDateTime;

public class EventRequestDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endTime;
    private int points;

    public EventRequestDto(Long id, LocalDateTime startDate, LocalDateTime endTime, int points) {
        this.id = id;
        this.startDate = startDate;
        this.endTime = endTime;
        this.points = points;
    }

    public EventRequestDto() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getPoints() {
        return points;
    }
}
