package com.bidding.crew.report;

import java.time.LocalDateTime;
import java.util.Optional;

public class Period {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Period(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Period() {
    }

    public Optional<Period> getCommonPeriod(Period userPeriod) {
        LocalDateTime commonStartTime = this.startTime.isAfter(userPeriod.getStartTime()) ? this.startTime : userPeriod.getStartTime();
        LocalDateTime commonEndTime = this.endTime.isBefore(userPeriod.getEndTime()) ? this.endTime : userPeriod.getEndTime();

        if (commonStartTime.isBefore(commonEndTime) || commonStartTime.isEqual(commonEndTime)) {
            return Optional.of(new Period(commonStartTime, commonEndTime));
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
