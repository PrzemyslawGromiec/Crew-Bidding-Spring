package com.bidding.crew.report;

import com.bidding.crew.flight.AircraftType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionCriteriaDto {
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private Duration minDuration = Duration.ZERO;
    private AircraftType aircraftType;

    public Period getPeriodDto() {
        return new Period(reportTime, clearTime);
    }
}
