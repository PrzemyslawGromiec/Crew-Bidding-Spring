package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import com.bidding.crew.event.EventRepository;
import com.bidding.crew.flight.FlightRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {
    private FlightRepository flightRepository;
    private EventRepository eventRepository;

    public RequestMapper(FlightRepository flightRepository, EventRepository eventRepository) {
        this.flightRepository = flightRepository;
        this.eventRepository = eventRepository;
    }

    /*
    * ReportFlight jest entity ktore posiada Flight
    * ReportFlightRequest - to ma id i gwiazdki czyli to co wybieram z listy
    * to co sobie wybralem zamieniam na entity
    * */
    public ReportFlight mapToEntity(ReportFlightRequest reportFlightRequest) {
        int id = reportFlightRequest.getFlightId();
        return new ReportFlight(flightRepository.findById(id).orElseThrow(), reportFlightRequest.getNumOfStars());
    }

    public ReportEvent mapToEntity(ReportEventDto reportEventDto) {
        List<Event> events = new ArrayList<>();
        Event event = new Event(reportEventDto.getStartTime(),reportEventDto.getEndTime(),reportEventDto.getStars(),
                "",false);

        events.add(event);

        return new ReportEvent(events, reportEventDto.getStars());
    }
}
