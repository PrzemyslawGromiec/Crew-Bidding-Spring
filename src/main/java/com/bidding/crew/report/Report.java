package com.bidding.crew.report;

import com.bidding.crew.general.Time;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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

    public Report(List<EventRequest> requests) {
        this.requests = new ArrayList<>(requests);
    }

    public ReportResponse toResponse() {
        List<EventRequestDto> eventRequests = getEventRequests().stream()
                .map(EventRequest::toDto)
                .toList();

        return new ReportResponse(id, closed, eventRequests, generatePeriods());
    }

    //posortowac requesty chronologicznie
    //ustawiamy 01. o 6.AM na start perioda
    //iterujac po requestach tworzymy period trwajacy do startu bierzacego requestu
    //aktualizujemy czas startu dla kolejnego perioda jako czas buforowanego konca bierzacego requestu

    List<PeriodDto> generatePeriods() {
        List<EventRequest> eventRequestList = getEventRequests();
        List<FlightRequest> flightRequestList = getFlightRequest();
        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(eventRequestList);
        allRequests.addAll(flightRequestList);
        //zakomentowalem ponizej, bo metoda testowa podwaja zawartosc requestow
        //requests.addAll(eventRequestList);
        //requests.addAll(flightRequestList);

        allRequests.sort(Comparator.comparing(Request::startTime));
        System.out.println(allRequests);

        List<PeriodDto> periods = new ArrayList<>();
        LocalDateTime currentTime = Time.getTime().startOfNextMonthDate();
        LocalDateTime endOfMonth = Time.getTime().endOfNextMonthDate();

        for (Request request : allRequests) {
            if (currentTime.isBefore(request.startTime())) {
                periods.add(new PeriodDto(currentTime, request.startTime()));
            }
            currentTime = request.endTimeBuffered();
        }

        if (currentTime.isBefore(endOfMonth)) {
            periods.add(new PeriodDto(currentTime, endOfMonth));
        }

        return periods;
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    private List<EventRequest> getEventRequests() {
        return requests.stream()
                .filter(request -> request instanceof EventRequest)
                .map(request -> (EventRequest) request)
                .toList();
    }

    private List<FlightRequest> getFlightRequest() {
        return requests.stream()
                .filter(r -> r instanceof FlightRequest)
                .map(request -> (FlightRequest) request)
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
