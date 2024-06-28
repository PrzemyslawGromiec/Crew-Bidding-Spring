package com.bidding.crew.report;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity

public class Period {
    @Id
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Period() {
    }

}
