package com.bidding.crew.report;

import com.bidding.crew.flight.AircraftType;

import java.time.Duration;
import java.time.LocalDateTime;

public class SuggestionCriteriaDto {

    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private Duration minDuration = Duration.ZERO;
    private AircraftType aircraftType;

    public SuggestionCriteriaDto() {
    }

    public PeriodDto getPeriodDto() {
        return new PeriodDto(reportTime, clearTime);
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public Duration getMinDuration() {
        return minDuration;
    }

    public LocalDateTime getClearTime() {
        return clearTime;
    }

    @Override
    public String toString() {
        return "SuggestionCriteriaDto{" +
                "reportTime=" + reportTime +
                ", clearTime=" + clearTime +
                ", minDuration=" + minDuration +
                ", aircraftType=" + aircraftType +
                '}';
    }
}
