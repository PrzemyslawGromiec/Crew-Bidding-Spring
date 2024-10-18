package com.bidding.crew.report;

import com.bidding.crew.general.Time;
import jakarta.persistence.*;
import lombok.Builder;

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
    private boolean closed;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests = new ArrayList<>();

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
                .eventRequest(eventRequests)
                .closed(closed);
    }

    List<PeriodDto> generatePeriods() {
        List<ReportEvent> reportEventList = getEventRequests();
        List<ReportFlight> reportFlightList = getFlightRequest();
        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(reportEventList);
        allRequests.addAll(reportFlightList);

        allRequests.sort(Comparator.comparing(Request::startTime));
        System.out.println(allRequests);

        List<PeriodDto> periods = new ArrayList<>();
        LocalDateTime currentStartTime = Time.getTime().startOfNextMonthDate();
        LocalDateTime currentEndTime;
        LocalDateTime endOfMonth = Time.getTime().endOfNextMonthDate();

        for (Request request : allRequests) {
            currentEndTime = request.startDate().atTime(LocalTime.of(22, 0, 0)).minusDays(1);
            if (currentStartTime.isBefore(request.startTime())) {
                //periods.add(new PeriodDto(currentStartTime, request.startTime()));
                periods.add(new PeriodDto(currentStartTime, currentEndTime));
            }
            currentStartTime = request.endTimeBuffered();
        }

        if (currentStartTime.isBefore(endOfMonth)) {
            periods.add(new PeriodDto(currentStartTime, endOfMonth));
        }

        return periods;
    }

    public void addRequest(Request request) {
        if(canAdd(request)) {
            requests.add(request);
        } else {
            throw new IllegalArgumentException("Request cannot be assigned to this period.");
        }
    }

    private boolean canAdd(Request request) {
        List<PeriodDto> periods = generatePeriods();
        for (PeriodDto period : periods) {
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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
