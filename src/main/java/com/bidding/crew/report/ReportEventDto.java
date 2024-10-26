package com.bidding.crew.report;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportEventDto {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int stars;

    public ReportEventDto(int id,LocalDateTime startTime, LocalDateTime endTime, int stars) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stars = stars;
    }

    public ReportEventDto(LocalDateTime startTime, LocalDateTime endTime, int stars) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.stars = stars;
    }

    public ReportEventDto() {
    }
}
