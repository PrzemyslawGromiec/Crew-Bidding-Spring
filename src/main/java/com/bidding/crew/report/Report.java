package com.bidding.crew.report;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean reportFinalized;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Period> periods = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests;

    public Report() {
    }


    public Report(List<EventRequest> requests, List<Period> periods) {
        this.requests = new ArrayList<>(requests);
        this.periods = periods;
    }

    public ReportDto toDto() {
        List<EventRequestDto> eventRequests = getEventRequests().stream()
                .map(EventRequest::toDto)
                .toList();

        return new ReportDto(id, reportFinalized,eventRequests);
    }

    private List<EventRequest> getEventRequests() {
        return requests.stream()
                .filter(request -> request instanceof EventRequest)
                .map(request -> (EventRequest) request)
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

    public boolean isReportFinalized() {
        return reportFinalized;
    }
}
