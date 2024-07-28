package com.bidding.crew.general;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Time {
    private static Time time = new Time();
    private static final int MONTHS_OFFSET = 0;

    private Time() {
    }

    public LocalDate nextMonthLocalDate() {
        return getToday().plusMonths(1);
    }

    public LocalDateTime nextMonthTime() {
        return nextMonthLocalDate().atTime(LocalTime.MIN);
    }

    public LocalDateTime startOfNextMonthDate() {
        return nextMonthTime().withDayOfMonth(1);
    }

    public LocalDateTime endOfNextMonthDate() {
        return LocalDate.now().plusMonths(2).atTime(LocalTime.MAX).withDayOfMonth(1).minusDays(1);
    }

    public static Time getTime() {
        return time;
    }

    private LocalDate getToday(){
        return LocalDate.now().plusMonths(MONTHS_OFFSET);
    }


}
