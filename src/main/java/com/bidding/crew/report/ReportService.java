package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.general.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private ReportRepository reportRepository;
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventService eventService;
    private FlightService flightService;
    private RequestMapper requestMapper;
    private ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository, EventService eventService,
                         FlightService flightService, RequestMapper requestMapper, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.eventService = eventService;
        this.flightService = flightService;
        this.requestMapper = requestMapper;
        this.reportMapper = reportMapper;
    }

    public List<ReportFlight> getFlightRequests() {
        return flightFactory.getRequests();
    }

    //todo:zaktualizowac przy implementowaniu powtarzajacych sie eventow
    private List<ReportEvent> getEventRequests() {
        return eventFactory.createRequests(eventService.getEvents());
    }

    ReportResponse createReport() {
        //List<ReportEvent> reportEvents = getEventRequests(); todo
        Report report = new Report(new ArrayList<>());
        reportRepository.save(report);
        return reportMapper.toResponse(report);
    }

    ReportResponse getReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report with id " + id + " not found"));

        return report.toResponse().build();
        //return reportMapper.toResponse(report);
    }

    private void calculatePoints(Report report) {
        PointsCalculator calculator = new PointsCalculator();
        calculator.interpretStarsAsPoints(report);
    }

    @Transactional
    ReportResponse finalizeReport(ReportRequest reportRequest) {
        //todo po wywaleniu eventow z raport eventow mozna wrocic i uprościć
        List<ReportEvent> reportEvents = reportRequest.getRequests().stream()
                .filter(requestDto -> requestDto.type().equals(RequestType.EVENT))
                .map(requestDto -> new ReportEvent(List.of(new Event(requestDto.startTime(), requestDto.endTime(),
                        requestDto.stars(), "")), requestDto.stars()))
                .toList();

        Report report = new Report(reportEvents);
        List<ReportFlight> flights = reportRequest.getRequests().stream()
                .filter(requestDto -> requestDto.type().equals(RequestType.FLIGHT))
                .map(requestDto -> new ReportFlight(
                        flightService.getFlightById(requestDto.id()), requestDto.stars()))
                .toList();


        report.addAllRequests(flights);
        calculatePoints(report);
        reportRepository.save(report);
        return report.toResponse().build();
    }

    public List<Period> getAllPeriods(Long reportId) {
        try {
            Report report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new ResourceNotFoundException("Report with id " + reportId + " not found"));
            return report.generatePeriods();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseConnectionException("Unable to connect to the database", e);
        }
    }

    public List<Period> generatePeriodsForReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new ResourceNotFoundException("Report with id" + reportId + " not found"));
        return report.generatePeriods();
    }

    public List<FlightDto> getSuggestedFlightsForPeriods(Long reportId, SuggestionCriteriaDto criteria) {
        if (criteria.getReportTime().isAfter(criteria.getClearTime())) {
            throw new InvalidPeriodException("Start time cannot be after end time.");
        }

        List<Period> commonTime = generatePeriodsForReport(reportId).stream()
                .map(criteria.getPeriodDto()::getCommonPeriod)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<FlightDto> suggestedFlights = commonTime.stream()
                .flatMap(periodDto -> flightService.getFlightsWithinPeriodWithMinDuration(criteria).stream())
                .map(Flight::toDto)
                .toList();

        if (suggestedFlights.isEmpty()) {
            throw new NoFlightSuggestionsException("No flight suggestions found for the given criteria.");
        }

        return suggestedFlights;
    }

    public ReportResponse saveFlight(Long id, ReportFlightRequest reportFlightRequest) {
        if (!isValidFlightData(reportFlightRequest)) {
            throw new InvalidFlightDataException("Invalid flight data provided.");
        }

        Report report = reportRepository.findById(id).orElseThrow();
        ReportFlight reportFlight = requestMapper.mapToEntity(reportFlightRequest);
        report.addRequest(reportFlight);
        reportRepository.save(report);
        return reportMapper.toResponse(report);
    }

    public ReportResponse saveEvent(Long id, ReportEventDto reportEventRequest) {
        Report report = reportRepository.findById(id).orElseThrow();
        ReportEvent reportEvent = requestMapper.mapToEntity(reportEventRequest);
        report.addRequest(reportEvent);
        reportRepository.save(report);
        return reportMapper.toResponse(report);
    }

    private boolean isValidFlightData(ReportFlightRequest reportFlightRequest) {
        if (reportFlightRequest.getFlightId() <= 0) {
            return false;
        }
        return reportFlightRequest.getNumOfStars() >= 1 && reportFlightRequest.getNumOfStars() <= 3;
    }
}




