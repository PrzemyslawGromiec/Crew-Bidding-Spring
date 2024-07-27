package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.general.Time;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RequestService {
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventService eventService;

    public RequestService(EventService eventService) {
        this.eventService = eventService;
    }

    public List<FlightRequest> getFlightRequests() {
        return flightFactory.getRequests();
    }

    public List<EventRequest> getEventRequests() {
        return eventFactory.createRequests(eventService.getEvents());
    }

/*    public void buildRequest(FlightDto chosenFlight, int priority) {
        flightFactory.buildRequest(chosenFlight,priority);
    }*/

}
