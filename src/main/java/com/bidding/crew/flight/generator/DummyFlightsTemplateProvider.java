package com.bidding.crew.flight.generator;

import com.bidding.crew.flight.AircraftType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DummyFlightsTemplateProvider implements FlightsTemplateProvider {

    @Override
    public List<FlightTemplate> provideFlights() {

        List<FlightTemplate> flightList = new ArrayList<>();
        flightList.add(new FlightTemplate("BA320", "MXP",LocalTime.of(5,10)));
        flightList.add(new FlightTemplate("BA100","CDG", LocalTime.of(5,25),LocalTime.of(15,10),1,
                List.of(DayOfWeek.MONDAY,DayOfWeek.WEDNESDAY), AircraftType.A319));
        return Collections.unmodifiableList(flightList);
    }
}
