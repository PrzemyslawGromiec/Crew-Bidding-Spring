package com.bidding.crew.report;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class ReportEventDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endTime;
    private int points;

    public ReportEventDto(Long id, LocalDateTime startDate, LocalDateTime endTime, int points) {
        this.id = id;
        this.startDate = startDate;
        this.endTime = endTime;
        this.points = points;
    }

    public ReportEventDto() {
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
