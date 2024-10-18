package com.bidding.crew.report;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportEventDto {
    private int id;
    private LocalDateTime startDate;
    private LocalDateTime endTime;
    private int stars;

    public ReportEventDto(int id, LocalDateTime startDate, LocalDateTime endTime, int stars) {
        this.id = id;
        this.startDate = startDate;
        this.endTime = endTime;
        this.stars = stars;
    }

    public ReportEventDto(LocalDateTime startDate, LocalDateTime endTime, int stars) {
        this.startDate = startDate;
        this.endTime = endTime;
        this.stars = stars;
    }

    public ReportEventDto() {
    }

}
