package com.bidding.crew.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PeriodDtoTest {

    @Test
    public void testNoCommonPeriod() {
        PeriodDto period1 = new PeriodDto(LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 10, 0, 0));
        PeriodDto period2 = new PeriodDto(LocalDateTime.of(2023, 7, 11, 0, 0),
                LocalDateTime.of(2023, 7, 20, 0, 0));

        Optional<PeriodDto> result = period1.getCommonPeriod(period2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPartialOverlapStart() {
        PeriodDto period1 = new PeriodDto(LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 10, 0, 0));
        PeriodDto period2 = new PeriodDto(LocalDateTime.of(2023, 7, 5, 0, 0),
                LocalDateTime.of(2023, 7, 15, 0, 0));

        Optional<PeriodDto> result = period1.getCommonPeriod(period2);
        assertTrue(result.isPresent());
        assertEquals(LocalDateTime.of(2023, 7, 5, 0, 0), result.get().getStartTime());
        assertEquals(LocalDateTime.of(2023, 7, 10, 0, 0), result.get().getEndTime());
    }

    @Test
    public void testPartialOverlapEnd() {
        PeriodDto period1 = new PeriodDto(LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 10, 0, 0));
        PeriodDto period2 = new PeriodDto(LocalDateTime.of(2023, 6, 25, 0, 0),
                LocalDateTime.of(2023, 7, 5, 0, 0));

        Optional<PeriodDto> result = period1.getCommonPeriod(period2);
        assertTrue(result.isPresent());
        assertEquals(LocalDateTime.of(2023, 7, 1, 0, 0), result.get().getStartTime());
        assertEquals(LocalDateTime.of(2023, 7, 5, 0, 0), result.get().getEndTime());
    }

    @Test
    public void testOnePeriodInsideAnother() {
        PeriodDto period1 = new PeriodDto(LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 10, 0, 0));
        PeriodDto period2 = new PeriodDto(LocalDateTime.of(2023, 7, 2, 0, 0),
                LocalDateTime.of(2023, 7, 5, 0, 0));

        Optional<PeriodDto> result = period1.getCommonPeriod(period2);
        assertTrue(result.isPresent());
        assertEquals(LocalDateTime.of(2023, 7, 2, 0, 0), result.get().getStartTime());
        assertEquals(LocalDateTime.of(2023, 7, 5, 0, 0), result.get().getEndTime());
    }

    @Test
    public void testExactMatch() {
        PeriodDto period1 = new PeriodDto(LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 10, 0, 0));
        PeriodDto period2 = new PeriodDto(LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 10, 0, 0));

        Optional<PeriodDto> result = period1.getCommonPeriod(period2);
        assertTrue(result.isPresent());
        assertEquals(LocalDateTime.of(2023, 7, 1, 0, 0), result.get().getStartTime());
        assertEquals(LocalDateTime.of(2023, 7, 10, 0, 0), result.get().getEndTime());
    }

    @Test
    public void testNonOverlappingPeriods() {
        PeriodDto period1 = new PeriodDto(LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 10, 0, 0));
        PeriodDto period2 = new PeriodDto(LocalDateTime.of(2023, 7, 11, 0, 0),
                LocalDateTime.of(2023, 7, 20, 0, 0));

        Optional<PeriodDto> result = period1.getCommonPeriod(period2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void test() {
        PeriodDto period1 = ReportTest.createPeriod(3, 0, 5, 23);
        PeriodDto period2 = ReportTest.createPeriod(7, 0, 7, 23);
        System.out.println(period2);
        Optional<PeriodDto> result = period2.getCommonPeriod(period1);
        assertTrue(result.isEmpty());
    }
}