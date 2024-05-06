package com.bidding.crew.flight.generator;

import java.util.List;

interface FlightsTemplateProvider {
    List<FlightTemplate> provideFlights();
}
