package com.bidding.crew.event.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration

public class MyConfig {

    @Value("${event.source}")
    private String eventSourceType;

    @Bean
    public TestEventSource provideEvents() {
        if ("CUSTOM".equalsIgnoreCase(eventSourceType)) {
            return new CustomEventsProvider();
        } else if ("RANDOM".equalsIgnoreCase(eventSourceType)) {
            return new RandomEventsProvider();
        }
        else {
            throw new IllegalArgumentException("Unknown service implementation" + eventSourceType);
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
