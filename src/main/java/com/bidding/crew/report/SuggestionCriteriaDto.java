package com.bidding.crew.report;

import com.bidding.crew.flight.AircraftType;
import jakarta.validation.constraints.Min;

import java.time.Duration;
import java.time.LocalDateTime;

public class SuggestionCriteriaDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration minDuration = Duration.ZERO;
    private AircraftType aircraftType;

    public SuggestionCriteriaDto() {
    }

    public PeriodDto getPeriodDto() {
        return new PeriodDto(startTime,endTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public Duration getMinDuration() {
        return minDuration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
