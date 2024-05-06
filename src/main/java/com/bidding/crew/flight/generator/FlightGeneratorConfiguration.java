package com.bidding.crew.flight.generator;

import com.bidding.crew.general.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

@Configuration
public class FlightGeneratorConfiguration {

    @Bean
    public FlightGeneratorFacade createFlightFacade(@Value("${flight.sourceType}") SourceType sourceType) throws FileNotFoundException {
        if (sourceType == SourceType.FILE) {
            return new FlightGeneratorFacade(new StringMapperFlightsTemplateProvider(),Time.getTime());
        } else if (sourceType == SourceType.DUMMY) {
            return new FlightGeneratorFacade(new DummyFlightsTemplateProvider(),Time.getTime());
        }
        throw new IllegalStateException("Source not implemented.");
    }
}
