package com.bidding.crew.report;

import java.time.LocalDateTime;
import java.util.Optional;

public class PeriodDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PeriodDto(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public PeriodDto() {
    }

    public Optional<PeriodDto> getCommonPeriod(PeriodDto userPeriod) {
        LocalDateTime commonStartTime = this.startTime.isAfter(userPeriod.getStartTime()) ? this.startTime : userPeriod.getStartTime();
        LocalDateTime commonEndTime = this.endTime.isBefore(userPeriod.getEndTime()) ? this.endTime : userPeriod.getEndTime();

        if (commonStartTime.isBefore(commonEndTime) || commonStartTime.isEqual(commonEndTime)) {
            return Optional.of(new PeriodDto(commonStartTime, commonEndTime));
        }

        return Optional.empty();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PeriodDto{" +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
