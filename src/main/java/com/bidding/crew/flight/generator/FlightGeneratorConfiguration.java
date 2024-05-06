package com.bidding.crew.flight.generator;

import com.bidding.crew.general.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

@Configuration
public class FlightGeneratorConfiguration {

    @Bean
    public FlightGeneratorFacade createFlightFacade(@Value("${flight.source}")Source source) throws FileNotFoundException {
        if (source == Source.FILE) {
            TextFileLoader textFileLoader = new TextFileLoader("Flights.txt");
            return new FlightGeneratorFacade(new StringMapperFlightsTemplateProvider(textFileLoader.readFile()), Time.getTime());
        } else if (source == Source.DUMMY) {
            return new FlightGeneratorFacade(new DummyFlightsTemplateProvider(),Time.getTime());
        }
        throw new IllegalStateException("Source not implemented.");
    }
}
