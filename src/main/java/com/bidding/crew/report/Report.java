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
    @OneToMany
    private List<Period> periods = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests;

    public Report() {
    }

    public Report(List<EventRequest> requests) {
        this.requests = new ArrayList<>(requests);
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
