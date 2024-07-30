package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.AircraftType;
import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.general.Time;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReportService {
    private ReportRepository reportRepository;
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventService eventService;
    private PeriodFactory periodFactory = new PeriodFactory();
    private FlightService flightService;
    private List<PeriodDto> periods;

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

    ReportResponse createReport(ReportRequest reportRequest) {
        if (reportRequest.isClosed()) {
            throw new RuntimeException("New created report cannot be finalized.");
        }

        List<EventRequest> eventRequests = getEventRequests();
        Report report = new Report(eventRequests);
        reportRepository.save(report);

        return report.toResponse();
        //List<Flight> flights = flightService.getFlightsForPeriod(periods.getFirst(), false);
    }

    ReportResponse getReport(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Report with id " + id + " not found"))
                .toResponse();

    }

    @Transactional
    ReportResponse updateStatus(Long id, ReportRequest reportRequest) {
        Report report = reportRepository.findById(id).orElseThrow();

        if (reportRequest.isClosed()) {
            report.setClosed(true);
        }

        if (!reportRequest.isClosed() && report.isClosed()) {
            throw new RuntimeException("You cannot open closed report.");
        }

        return report.toResponse();
    }

    public List<PeriodDto> getAllPeriods(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        return report.toResponse().getPeriods();
    }

    public List<PeriodDto> generatePeriodsForReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        return report.generatePeriods();
    }

    public List<FlightDto> getSuggestedFlightsForPeriods(Long reportId, PeriodDto period, Duration minDuration, AircraftType aircraftType) {
        List<PeriodDto> commonTime  = generatePeriodsForReport(reportId).stream()
                .map(period::getCommonPeriod)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

       return commonTime.stream()
                .flatMap( periodDto -> flightService.getFlightsWithinPeriodWithMinDuration(periodDto, minDuration, aircraftType).stream())
                .map(Flight::toDto)
                .toList();
    }
}





