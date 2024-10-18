package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import com.bidding.crew.event.EventDto;
import com.bidding.crew.event.EventRepository;
import com.bidding.crew.flight.FlightRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {
    private FlightRepository flightRepository;

    public RequestMapper(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public ReportFlight mapToEntity(ReportFlightRequest reportFlightRequest) {
        int id = reportFlightRequest.getFlightId();
        return new ReportFlight(flightRepository.findById(id).orElseThrow(), reportFlightRequest.getNumOfStars());
    }

    public ReportEvent mapToEntity(ReportEventDto reportEventDto) {
        List<Event> events = new ArrayList<>();
        Event event = new Event(reportEventDto.getStartDate(),reportEventDto.getEndTime(),reportEventDto.getStars(),
                "",false);
        events.add(event);
        return new ReportEvent(events,reportEventDto.getStars());
    }
}
