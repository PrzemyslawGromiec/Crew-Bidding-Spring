package com.bidding.crew.report;

import com.bidding.crew.general.Time;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests = new ArrayList<>();
    private static final LocalTime DAY_START = LocalTime.of(6, 0);
    private static final LocalTime DAY_END = LocalTime.MAX;

    public Report() {
    }

    public Report(List<ReportEvent> requests) {
        this.requests = new ArrayList<>(requests);
    }

    public ReportResponse.ReportResponseBuilder toResponse() {
        List<ReportEventDto> eventRequests = getEventRequests().stream()
                .map(ReportEvent::toDto)
                .toList();

        List<ReportFlightResponse> flightRequests = getFlightRequest().stream()
                .map(ReportFlight::toDto)
                .toList();

        return ReportResponse.builder()
                .id(id)
                .flightRequests(flightRequests)
                .eventRequest(eventRequests);
    }

    List<Period> generatePeriods() {
        // 1) Collect all requests (events and flights) and sort them by start time
        List<ReportEvent> reportEventList = getEventRequests();
        List<ReportFlight> reportFlightList = getFlightRequest();
        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(reportEventList);
        allRequests.addAll(reportFlightList);

        allRequests.sort(Comparator.comparing(Request::startTime));
        System.out.println(allRequests);
        List<Period> periods = new ArrayList<>();

        // 2) Define the planning window for the next month, aligned to business day bounds
        LocalDateTime windowStart = atDayStart(Time.getTime().startOfNextMonthDate().toLocalDate());
        LocalDateTime windowEnd = atDayEnd(Time.getTime().endOfNextMonthDate().toLocalDate());

        // 3) 'cursor' points to the start of the current free window we’re building
        LocalDateTime cursor = windowStart;

        // 4) Walk through all requests and carve out free periods before each request
        for (Request r : allRequests) {
            // 4a) Skip requests that end entirely before the planning window
            if (r.endTime().isBefore(windowStart)) {
                continue;
            }
            // 4b) If the next request starts after the window, we can finish (no more splits needed)
            if (r.startTime().isAfter(windowEnd)) {
                break;
            }
            // 4c) Free time ends the day BEFORE the request starts, at DAY_END (e.g. 23:59:59)
            LocalDate prevDay = r.startTime().toLocalDate().minusDays(1);
            LocalDateTime freeEnd = atDayEnd(prevDay);

            // 4d) Clip the free end to the window upper bound
            freeEnd = min(freeEnd, windowEnd);

            // 4e) If there is a non-empty gap between 'cursor' and 'freeEnd', add a free period
            if (freeEnd.isAfter(cursor)) {
                periods.add(new Period(cursor, freeEnd));
            }

            // 4f) Move 'cursor' to the day AFTER the request ends, at DAY_START (e.g. 06:00)
            LocalDate nextDay = r.endTime().toLocalDate().plusDays(1);
            cursor = max(cursor, atDayStart(nextDay));

            // 4g) If we’ve moved past the window, stop early
            if (cursor.isAfter(windowEnd)) break;
        }

        // 5) Tail: after the last request, add the remaining free window up to the end of month
        if (cursor.isBefore(windowEnd)) {
            periods.add(new Period(cursor, windowEnd));
        }

        return periods;
    }

    private static LocalDateTime atDayStart(LocalDate d) {
        return d.atTime(DAY_START);
    }

    private static LocalDateTime atDayEnd(LocalDate d) {
        return d.atTime(DAY_END);
    }

    private static LocalDateTime min(LocalDateTime a, LocalDateTime b) {
        return a.isBefore(b) ? a : b;
    }

    private static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        return a.isAfter(b) ? a : b;
    }

    public void addRequest(Request request) {
        if (canAdd(request)) {
            requests.add(request);
        } else {
            throw new IllegalArgumentException("Request cannot be assigned to this period.");
        }
    }

    public void addAllRequests(List<? extends Request> requests) {
        requests.forEach(this::addRequest);
    }

    private boolean canAdd(Request request) {
        List<Period> periods = generatePeriods();
        for (Period period : periods) {
            if ((request.startTime().isAfter(period.getStartTime()) &&
                    request.endTime().isBefore(period.getEndTime()))) {
                return true;

            }
        }
        return false;
    }

    private List<ReportEvent> getEventRequests() {
        return requests.stream()
                .filter(request -> request instanceof ReportEvent)
                .map(request -> (ReportEvent) request)
                .toList();
    }

    private List<ReportFlight> getFlightRequest() {
        return requests.stream()
                .filter(r -> r instanceof ReportFlight)
                .map(request -> (ReportFlight) request)
                .toList();
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
