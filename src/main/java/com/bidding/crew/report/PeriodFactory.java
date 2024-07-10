package com.bidding.crew.report;

import com.bidding.crew.general.Time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class PeriodFactory {

    private Time time = Time.getTime();

    public List<Period> createPeriodsBetweenRequests(List<EventRequest> requests) {
        List<Period> createdPeriods = new ArrayList<>();

        if (requests.isEmpty()) {
            Period fullMonthPeriod = nextMonthPeriod();
            return Collections.singletonList(fullMonthPeriod);
        }

        EventRequest firstRequest = requests.getFirst();
        if (firstRequest.startTime().getDayOfMonth() != 1) {
            Period firstDayOfMonthPeriod = nextMonthPeriod(firstRequest.startTime());
            createdPeriods.add(firstDayOfMonthPeriod);
        }

        EventRequest previousEventRequest = firstRequest;
        for (int i = 1; i < requests.size(); i++) {
            EventRequest currentEventRequest = requests.get(i);
            Period betweenEventsPeriod = nextMonthPeriod(previousEventRequest.endTime(),currentEventRequest.startTime());
            if (betweenEventsPeriod.getStartTime().isBefore(betweenEventsPeriod.getEndTime())) {
                createdPeriods.add(betweenEventsPeriod);
            }
            previousEventRequest = currentEventRequest;
        }

        LocalDateTime endOfMonth = time.startOfNextMonthDate().plusMonths(1).minusMinutes(1);
        Period lastPeriod = nextMonthPeriod(previousEventRequest.endTime(), endOfMonth);
        if (lastPeriod.getStartTime().isBefore(lastPeriod.getEndTime())) {
            createdPeriods.add(lastPeriod);
        }

        return createdPeriods;
    }

    private Period nextMonthPeriod() {
        return nextMonthPeriod(time.startOfNextMonthDate());
    }

    private Period nextMonthPeriod(LocalDateTime endBeforeThis) {
        return nextMonthPeriod(time.nextMonthTime().withDayOfMonth(1).minusDays(1),endBeforeThis);
    }

    private Period nextMonthPeriod(LocalDateTime startAfterThis, LocalDateTime endBeforeThis) {
        return new Period(startAfterThis.toLocalDate().plusDays(1).atTime(LocalTime.of(6,0,0)),
                endBeforeThis.toLocalDate().atTime(LocalTime.MAX).minusDays(1));
    }
}
