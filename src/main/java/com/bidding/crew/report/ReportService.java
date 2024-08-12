package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.AircraftType;
import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.general.*;
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
            throw new InvalidReportStateException("New created report cannot be finalized.");
        }

        List<ReportEvent> reportEvents = getEventRequests();
        Report report = new Report(reportEvents);
        reportRepository.save(report);

        return report.toResponse();
        //List<Flight> flights = flightService.getFlightsForPeriod(periods.getFirst(), false);
    }

    ReportResponse getReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report with id " + id + " not found"));

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
        Report report = reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report with id " + id + " not found."));

        if (reportRequest.isClosed()) {
            report.setClosed(true);
        }

        if (!reportRequest.isClosed() && report.isClosed()) {
            throw new InvalidReportStateException("You cannot open finalized report.");
        }

        calculatePoints(report);
        return report.toResponse();
    }

    public List<PeriodDto> getAllPeriods(Long reportId) {
        try {
            Report report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new ResourceNotFoundException("Report with id " + reportId + " not found"));
            return report.toResponse().getPeriods();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseConnectionException("Unable to connect to the database", e);
        }
    }

    public List<PeriodDto> generatePeriodsForReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new ResourceNotFoundException("Report with id" + reportId +" not found"));
        return report.generatePeriods();
    }

    public List<FlightDto> getSuggestedFlightsForPeriods(Long reportId, SuggestionCriteriaDto criteria) {
        if (criteria.getReportTime().isAfter(criteria.getClearTime())) {
            throw new InvalidPeriodException("Start time cannot be after end time.");
        }

        List<PeriodDto> commonTime = generatePeriodsForReport(reportId).stream()
                .map(criteria.getPeriodDto()::getCommonPeriod)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<FlightDto> suggestedFlights = commonTime.stream()
                .flatMap(periodDto -> flightService.getFlightsWithinPeriodWithMinDuration(criteria).stream())
                .map(Flight::toDto)
                .toList();

        /*List<FlightDto> suggestedFlights = commonTime.stream()
                .flatMap(periodDto -> flightService.getSuggestedFlightsWithSpecification(criteria).stream())
                .toList();*/


        if (suggestedFlights.isEmpty()) {
            throw new NoFlightSuggestionsException("No flight suggestions found for the given criteria.");
        }

        return suggestedFlights;
    }

    public ReportFlightResponse saveFlight(Long id, ReportFlightRequest reportFlightRequest) {
        if (!isValidFlightData(reportFlightRequest)) {
            throw new InvalidFlightDataException("Invalid flight data provided.");
        }

        Report report = reportRepository.findById(id).orElseThrow();
        ReportFlight reportFlight = requestMapper.mapToEntity(reportFlightRequest);
        report.addRequest(reportFlight);
        reportRepository.save(report);
        return reportFlight.toDto();
    }

    private boolean isValidFlightData(ReportFlightRequest reportFlightRequest) {
        if (reportFlightRequest.getFlightId() <= 0) {
            return false;
        }
        return reportFlightRequest.getNumOfStars() >= 1 && reportFlightRequest.getNumOfStars() <= 3;
    }

}




