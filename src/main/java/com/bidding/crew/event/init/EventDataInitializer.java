package com.bidding.crew.event.init;

import com.bidding.crew.event.EventService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class EventDataInitializer implements CommandLineRunner {

    private final EventService eventService;
    private TestEventSource testEventSource;

    public EventDataInitializer(EventService eventService, TestEventSource testEventSource) {
        this.eventService = eventService;
        this.testEventSource = testEventSource;
    }

    @Override
    public void run(String... args) throws Exception {
        eventService.saveAllEvents(testEventSource.createEvents());
    }
}

//Dostarcza nam liste eventów
//przez propertisy zmieniamy źródło
//mamy 2 zrodla, klase robiącą to losowo i na sztywno
//zmieniamy regulując parametr testEventsSource
