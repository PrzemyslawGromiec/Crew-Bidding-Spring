package com.bidding.crew.flight.generator;


import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.general.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightGeneratorFacade {
    private FlightsTemplateProvider flightsTemplateProvider;
    private Time time;

    FlightGeneratorFacade(FlightsTemplateProvider flightsTemplateProvider, Time time) {
        this.flightsTemplateProvider = flightsTemplateProvider;
        this.time = time;
    }

    public List<Flight> generateFlights(String... source) {
        List<FlightTemplate> flightTemplates = flightsTemplateProvider.provideFlights(source);
        List<Flight> flights = new ArrayList<>();
        for (FlightTemplate flightTemplate : flightTemplates) {
            flights.addAll(generateCustomRecurringFlights(flightTemplate));
        }
        return flights;
    }

    private List<Flight> generateCustomRecurringFlights(FlightTemplate flightTemplate) {
        List<Flight> flights = new ArrayList<>();
        LocalDate nextMonth = time.nextMonthLocalDate();
        for (int day = 1; day <= nextMonth.getMonth().maxLength(); day++) {
            if (!flightTemplate.flightsOn(nextMonth.withDayOfMonth(day).getDayOfWeek())) {
                continue;
            }
            LocalDateTime report = LocalDateTime.of(nextMonth.withDayOfMonth(day), flightTemplate.getReportTime());
            LocalDateTime clear = LocalDateTime.of(nextMonth.withDayOfMonth(day).plusDays(flightTemplate.getDurationDays()), flightTemplate.getClearTime());
            FlightDto flightDto = new FlightDto(flightTemplate.getFlightNumber(), flightTemplate.getAirportCode(), report, clear, flightTemplate.getAircraftType());
            flights.add(new Flight(flightDto));
        }
        return flights;
    }

}