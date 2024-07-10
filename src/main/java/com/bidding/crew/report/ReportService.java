package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.general.Time;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//todo: koncowy period nie istnieje
@Service
public class ReportService {
    private ReportRepository reportRepository;
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventService eventService;
    private PeriodFactory periodFactory = new PeriodFactory();
    private FlightService flightService;

    //todo:kolejne periody, all durations
    public ReportService(ReportRepository reportRepository, EventService eventService, FlightService flightService) {
        this.reportRepository = reportRepository;
        this.eventService = eventService;
        this.flightService = flightService;
    }

    public List<FlightRequest> getFlightRequests() {
        return flightFactory.getRequests();
    }

    private List<EventRequest> getEventRequests() {
        return eventFactory.createRequests(eventService.getEvents());
    }

    ReportDto applyAction(ActionType actionType){
        if (reportRepository.count() !=0 && !reportRepository.findAll().getFirst().isReportFinalized()) {
            throw new IllegalStateException("Not finalized report exists.");
        }

        List<EventRequest> eventRequests = getEventRequests();
        List<Period> periods = periodFactory.createPeriodsBetweenRequests(eventRequests);

        Report report = new Report(eventRequests,periods);
        reportRepository.save(report);
        List<Flight> flights = flightService.getFlightsForPeriod(periods.getFirst(), false);
        return report.toDto(flights);
    }

    //wpisywanie eventow
    //scalanie je w jeden wiekszy event jesli sa w podobnym przedziale czasowym i tym samym priority
    //tworzenie periodow dookola powstalych eventow
    //wyswietlanie lotow ktore pasuja w danym periodzie
    //wybieranie lotow lub pomijanie w danym periodzie
    //






}
