package com.bidding.crew.report;

import com.bidding.crew.event.EventService;
import com.bidding.crew.flight.Flight;
import com.bidding.crew.flight.FlightDto;
import com.bidding.crew.flight.FlightService;
import com.bidding.crew.general.Time;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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


   /* public List<FlightDto> getSuggestedFlightsForPeriods(Long reportId, PeriodDto period) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        //todo: test
        List<PeriodDto> periods = generatePeriodsForReport(reportId);
        List<PeriodDto> commonTime = new ArrayList<>();
        for (PeriodDto periodDto : periods) {
            period.getCommonPeriod(periodDto).ifPresent(commonTime::add);
        }
        System.out.println(commonTime);

        List<FlightDto> suggestions = new ArrayList<>();
        for (PeriodDto periodDto : commonTime) {
            suggestions.addAll(flightService.getFlightsForPeriod(periodDto,true).stream()
                    .map(Flight::toDto)
                    .toList());
        }

        return suggestions;
    }*/

    public List<FlightDto> getSuggestedFlightsForPeriods(Long reportId, PeriodDto period, Duration minDuration) {
        List<PeriodDto> periods = generatePeriodsForReport(reportId);
        List<PeriodDto> commonTime = new ArrayList<>();
        for (PeriodDto periodDto : periods) {
            period.getCommonPeriod(periodDto).ifPresent(commonTime::add);
        }
        System.out.println(commonTime);

        List<FlightDto> suggestions = new ArrayList<>();
        for (PeriodDto periodDto : commonTime) {
            suggestions.addAll(flightService.getFlightsWithinPeriodWithMinDuration(periodDto,minDuration)
                    .stream()
                    .map(Flight::toDto)
                    .toList());
        }

        return suggestions;
    }

/*    private List<PeriodDto> getCommonPeriods(PeriodDto period, List<PeriodDto> generatedPeriods) {
        LocalDateTime start = period.getStartTime();
        LocalDateTime end = period.getEndTime();

        //przejsc przez generatedPeriods i sprawdzic czy ktorys z nich pokrywa sie z data podana przez uzytkownika
        for (PeriodDto generatedPeriod : generatedPeriods) {
            if ()
        }

        return null;
    }*/


}

/*   public List<FlightDto> getSuggestedFlightsInPeriod(Long reportId, Long periodId) {
 *//*PeriodDto period = getAllPeriods(reportId).stream()
                .filter(p -> p.getId().equals(periodId))
                .findFirst()
                .orElseThrow(()->new NoSuchElementException("Period not found"));
       *//*
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NoSuchElementException("Report not found"));

        Period period = report.generatePeriods().stream()
                .filter(p -> p.getId().equals(periodId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Period not found"));

        List<Flight> suggestedFlights = flightService.getFlightsForPeriod(period, true);
        return suggestedFlights.stream()
                .map(Flight::toDtoWithId)
                .toList();
    }*/

 /*   @Transactional
    public ReportResponse selectFlightForPeriod(Long reportId, Long periodId, FlightDto selectedFlightDto) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NoSuchElementException("Report not found"));

        Period period = report.getPeriods().stream()
                .filter(p -> p.getId().equals(periodId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Period not found in the report"));

        Flight selectedFlight = flightService.getFlightById(selectedFlightDto.getId());

        Period period1 = new Period(period.getStartTime(), selectedFlight.getReportTime());
        Period period2 = new Period(selectedFlight.getClearTimeWithBuffer(), period.getEndTime());

        report.getPeriods().remove(period);
        if (period1.getStartTime().isBefore(period1.getEndTime())) {
            report.getPeriods().add(period1);
        }
        if (period2.getStartTime().isBefore(period2.getEndTime())) {
            report.getPeriods().add(period2);
        }

        reportRepository.save(report);
        return report.toResponse();
    }*/



