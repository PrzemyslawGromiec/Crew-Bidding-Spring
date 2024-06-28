package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.general.Time;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    private ReportRepository reportRepository;
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventService eventService;

    public ReportService(ReportRepository reportRepository, EventService eventService) {
        this.reportRepository = reportRepository;
        this.eventService = eventService;
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

        Report report = new Report(getEventRequests());
        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportRepository.save(report);
        return reportDto;
    }

    //wpisywanie eventow
    //scalanie je w jeden wiekszy event jesli sa w podobnym przedziale czasowym i tym samym priority
    //tworzenie periodow dookola powstalych eventow
    //wyswietlanie lotow ktore pasuja w danym periodzie
    //wybieranie lotow lub pomijanie w danym periodzie
    //

    //event service
    //create request
    //generowanie periodow
    //zwrocic jako dto





}
