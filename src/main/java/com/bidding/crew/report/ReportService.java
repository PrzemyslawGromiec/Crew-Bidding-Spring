package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.general.Time;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    private RequestMapper requestMapper;

    public ReportService(ReportRepository reportRepository, EventService eventService, FlightService flightService, RequestMapper requestMapper) {
        this.reportRepository = reportRepository;
        this.eventService = eventService;
        this.flightService = flightService;
        this.requestMapper = requestMapper;
    }

    public List<ReportFlight> getFlightRequests() {
        return flightFactory.getRequests();
    }

    private List<ReportEvent> getEventRequests() {
        return eventFactory.createRequests(eventService.getEvents());
    }

    ReportResponse createReport(ReportRequest reportRequest) {
        if (reportRequest.isClosed()) {
            throw new RuntimeException("New created report cannot be finalized.");
        }

        List<ReportEvent> reportEvents = getEventRequests();
        Report report = new Report(reportEvents);
        reportRepository.save(report);

        return report.toResponse();
        //List<Flight> flights = flightService.getFlightsForPeriod(periods.getFirst(), false);
    }

    ReportResponse getReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Report with id " + id + " not found"));

        if (report.isClosed()) {
            calculatePoints(report);
        }
        return report.toResponse();
    }

    private void calculatePoints(Report report) {
        PointsCalculator calculator = new PointsCalculator();
        calculator.interpretStarsAsPoints(report);
    }

    @Transactional
    ReportResponse updateStatus(Long id, ReportRequest reportRequest) {
        Report report = reportRepository.findById(id).orElseThrow();

        if (reportRequest.isClosed()) {
            report.setClosed(true);
        }

        if (!reportRequest.isClosed() && report.isClosed()) {
            throw new RuntimeException("You cannot open finalized report.");
        }

        calculatePoints(report);
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

    public List<FlightDto> getSuggestedFlightsForPeriods(Long reportId, SuggestionCriteriaDto criteria) {
        List<PeriodDto> commonTime = generatePeriodsForReport(reportId).stream()
                .map(criteria.getPeriodDto()::getCommonPeriod)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return commonTime.stream()
                .flatMap(periodDto -> flightService.getFlightsWithinPeriodWithMinDuration(criteria).stream())
                .map(Flight::toDto)
                .toList();
    }

    public ReportFlightResponse saveFlight(Long id, ReportFlightRequest reportFlightRequest) {
        Report report = reportRepository.findById(id).orElseThrow();
        ReportFlight reportFlight = requestMapper.mapToEntity(reportFlightRequest);
        report.addRequest(reportFlight);
        reportRepository.save(report);
        return reportFlight.toDto();
    }

}




