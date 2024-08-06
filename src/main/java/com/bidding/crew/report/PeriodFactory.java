package com.bidding.crew.report;

import com.bidding.crew.general.Time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class PeriodFactory {

    private Time time = Time.getTime();

    public List<PeriodDto> createPeriodsBetweenRequests(List<ReportEvent> requests) {
        List<PeriodDto> createdPeriods = new ArrayList<>();

        if (requests.isEmpty()) {
            PeriodDto fullMonthPeriod = nextMonthPeriod();
            return Collections.singletonList(fullMonthPeriod);
        }

        ReportEvent firstRequest = requests.getFirst();
        if (firstRequest.startTime().getDayOfMonth() != 1) {
            PeriodDto firstDayOfMonthPeriod = nextMonthPeriod(firstRequest.startTime());
            createdPeriods.add(firstDayOfMonthPeriod);
        }

        ReportEvent previousReportEvent = firstRequest;
        for (int i = 1; i < requests.size(); i++) {
            ReportEvent currentReportEvent = requests.get(i);
            PeriodDto betweenEventsPeriod = nextMonthPeriod(previousReportEvent.endTime(), currentReportEvent.startTime());
            if (betweenEventsPeriod.getStartTime().isBefore(betweenEventsPeriod.getEndTime())) {
                createdPeriods.add(betweenEventsPeriod);
            }
            previousReportEvent = currentReportEvent;
        }

        LocalDateTime endOfMonth = time.startOfNextMonthDate().plusMonths(1).minusMinutes(1);
        PeriodDto lastPeriod = nextMonthPeriod(previousReportEvent.endTime(), endOfMonth);
        if (lastPeriod.getStartTime().isBefore(lastPeriod.getEndTime())) {
            createdPeriods.add(lastPeriod);
        }

        return createdPeriods;
    }

    private PeriodDto nextMonthPeriod() {
        return nextMonthPeriod(time.startOfNextMonthDate());
    }

    private PeriodDto nextMonthPeriod(LocalDateTime endBeforeThis) {
        return nextMonthPeriod(time.nextMonthTime().withDayOfMonth(1).minusDays(1),endBeforeThis);
    }

    private PeriodDto nextMonthPeriod(LocalDateTime startAfterThis, LocalDateTime endBeforeThis) {
        return new PeriodDto(startAfterThis.toLocalDate().plusDays(1).atTime(LocalTime.of(6,0,0)),
                endBeforeThis.toLocalDate().atTime(LocalTime.MAX).minusDays(1));
    }
}
