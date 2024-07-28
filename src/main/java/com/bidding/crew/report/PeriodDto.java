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

    //todo:problem z liczeniem periodow
   /* public Optional<PeriodDto> getCommonPeriod(PeriodDto userPeriod) {

        if((this.getEndTime().isAfter(userPeriod.getStartTime())
                && userPeriod.getStartTime().isBefore(this.endTime))
        || (userPeriod.getEndTime().isAfter(this.startTime) && (this.startTime.isBefore(userPeriod.getEndTime())))) {
            return Optional.of(new PeriodDto(userPeriod.getStartTime(),this.endTime));
        }
        if((this.getEndTime().isBefore(userPeriod.startTime))|| (userPeriod.endTime.isBefore(this.startTime))) {
            return Optional.empty();
        }
        if(userPeriod.getStartTime().isAfter(this.startTime)
                && userPeriod.getEndTime().isBefore(this.endTime)) {
            return Optional.of(new PeriodDto(userPeriod.getStartTime(),userPeriod.getEndTime()));
        }
        if((this.startTime.isAfter(userPeriod.getStartTime())
                && this.endTime.isBefore(userPeriod.getEndTime())) ){
            return Optional.of(new PeriodDto(this.startTime,this.endTime));
        }
        if(this.startTime.isEqual(userPeriod.startTime)
                && (this.endTime.isEqual(userPeriod.endTime))) {
            return Optional.of(new PeriodDto(this.startTime,this.endTime));
        }
        return Optional.empty();
    }*/

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
