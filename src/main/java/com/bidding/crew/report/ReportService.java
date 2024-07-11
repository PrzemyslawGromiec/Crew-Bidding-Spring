package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.general.Time;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportService {
    private ReportRepository reportRepository;
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventService eventService;
    private PeriodFactory periodFactory = new PeriodFactory();
    private FlightService flightService;
    private List<Period> periods;
    private int currentPeriodIndex = 0;

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

    ReportResponse createReport(ReportRequest reportRequest){
        if(reportRequest.isClosed()) {
            throw new RuntimeException("New created report cannot be finalized.");
        }
        /*if (reportRepository.count() !=0 && !reportRepository.findAll().getFirst().isReportFinalized()) {
            throw new IllegalStateException("Not finalized report exists.");
        }*/

        List<EventRequest> eventRequests = getEventRequests();
        List<Period> periods = periodFactory.createPeriodsBetweenRequests(eventRequests);

        Report report = new Report(eventRequests,periods);
        reportRepository.save(report);

        return report.toResponse();
        //List<Flight> flights = flightService.getFlightsForPeriod(periods.getFirst(), false);
    }

    ReportResponse getReport(Long id) {
        return reportRepository.findById(id).orElseThrow().toResponse();
    }

    @Transactional
    ReportResponse updateStatus(Long id, ReportRequest reportRequest) {
        Report report = reportRepository.findById(id).orElseThrow();

        if(reportRequest.isClosed()) {
            report.setClosed(true);
        }

        if(!reportRequest.isClosed() && report.isClosed()) {
            throw new RuntimeException("You cannot open closed report.");
        }

        return report.toResponse();
    }


}
