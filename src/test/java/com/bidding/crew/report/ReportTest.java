package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import com.bidding.crew.event.EventDto;
import com.bidding.crew.flight.AircraftType;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.general.Time;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.bidding.crew.flight.Flight;

class ReportTest {

    @Test
    void generatePeriods() {
        Report report = new Report();

        //posortowac requesty chronologicznie
        //ustawiamy 01. o 6.AM na start perioda
        //iterujac po requestach tworzymy period trwajacy do startu bierzacego requestu
        //aktualizujemy czas startu dla kolejnego perioda jako czas buforowanego konca bierzacego requestu

        //time off
        report.addRequest(createEventRequest(3, 3));
        report.addRequest(createEventRequest(7, 1));
        report.addRequest(createEventRequest(12, 2));

        //flight requests
        report.addRequest(createFlightRequest(1, 12, 8));
        report.addRequest(createFlightRequest(9, 8, 8));
        report.addRequest(createFlightRequest(20, 8, 8));
        //1. 01.08 12:00 - 01.08 20:00 - flight + 12h - ok
        //2. 02.08 08:00 - 03.08 00:00 - period - ok
        //3. 03.08 00:00 - 05.08 23:59 - event + 6h - ok
        //4. 06.08 06:00 - 07.08 00:00 - period - ok
        //5. 07.08 00:00 - 07.08 23:59 - event + 6h - ok
        //6. 08.08 06:00 - 09.08 08:00 - period - ok
        //7. 09.08 08:00 - 09.08 16:00 - lot + 12h - ok
        //8. 10.08 04:00 - 12.08 00:00 - period - ok
        //9. 12.08 00:00 - 13.08 23:59 - event + 6h - ok
        //10. 14.08 06:00 - 20.08 08:00 - period - ok
        //11. 20.08 08:00 - 20.08 16:00 - lot - ok

        List<PeriodDto> periods = report.generatePeriods();
        System.out.println(periods);
        System.out.println(periods.size());

        Assertions.assertTrue(periods.contains(createPeriod(2, 8, 3, 0)));
        //Assertions.assertTrue(periods.contains(createPeriod(2, 8, 3, 0)));
        //Assertions.assertTrue(periods.contains(createPeriod(3, 0, 5, 23)));
        Assertions.assertTrue(periods.contains(createPeriod(6, 6, 7, 0)));
        Assertions.assertTrue(periods.contains(createPeriod(7, 0, 7, 23)));
        //Assertions.assertTrue(periods.contains(createPeriod(8, 6, 9, 8)));
        Assertions.assertTrue(periods.contains(createPeriod(9, 8, 9, 16)));
        Assertions.assertTrue(periods.contains(createPeriod(10, 4, 12, 0)));
        Assertions.assertTrue(periods.contains(createPeriod(12, 0, 13, 23)));
        //Assertions.assertTrue(periods.contains(createPeriod(14, 6, 20, 8)));
        Assertions.assertTrue(periods.contains(createPeriod(20, 8, 20, 16)));
        Assertions.assertTrue(periods.contains(createPeriod(21, 4, 31, 23)));
        Assertions.assertEquals(periods.size(), 12);

    }

    public static PeriodDto createPeriod(int startDay, int startHour, int endDay, int endHour) {
        if(endHour == 23) {
            return new PeriodDto(Time.getTime().nextMonthTime().withDayOfMonth(startDay).withHour(startHour),
                    Time.getTime().nextMonthLocalDate().atTime(LocalTime.MAX).withDayOfMonth(endDay));
        }
        return new PeriodDto(Time.getTime().nextMonthTime().withDayOfMonth(startDay).withHour(startHour),
                Time.getTime().nextMonthTime().withDayOfMonth(endDay).plusHours(endHour));
    }

    private ReportEvent createEventRequest(int startDayOfMonth, int days, int numOfStars) {
        LocalDateTime startTime = Time.getTime().nextMonthLocalDate().atTime(LocalTime.MIN).withDayOfMonth(startDayOfMonth);
        // jest -1, bo jak event ma trwac 3 dni, to chce zeby byl np od 03.08 00:00 do 05.08 23:59
        LocalDateTime endTime = Time.getTime().nextMonthLocalDate().atTime(LocalTime.MAX).withDayOfMonth(startDayOfMonth).plusDays(days - 1);
        Event event = new EventDto(startTime, endTime, numOfStars, "uni", false).toEntity();
        return new ReportEvent(List.of(event));

    }

    private ReportEvent createEventRequest(int startDayOfMonth, int days) {
        return createEventRequest(startDayOfMonth, days, 3);
    }

    private ReportFlight createFlightRequest(int startDayOfMonth, int startingHour, int hoursFlight, int numOfStars) {
        Flight flight = createFlight(startDayOfMonth, startingHour, hoursFlight);
        return new ReportFlight(flight, numOfStars);
    }

    private ReportFlight createFlightRequest(int startDayOfMonth, int startingHour, int hoursFlight) {
        return createFlightRequest(startDayOfMonth, startingHour, hoursFlight, 3);
    }


    private Flight createFlight(int startDayOfMonth, int startingHour, int hoursFlight) {
        FlightDto flightDto = new FlightDto(0,"", "", LocalDateTime.of(2024, 8, startDayOfMonth, startingHour, 0),
                LocalDateTime.of(2024, 8, startDayOfMonth, startingHour, 0).plusHours(hoursFlight), AircraftType.A320);
        return new Flight(flightDto);
    }
}