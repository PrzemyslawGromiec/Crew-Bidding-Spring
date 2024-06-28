package com.bidding.crew.report;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Random;

@Entity
public abstract class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int points = new Random().nextInt();

    public abstract LocalDateTime startTime();
    public abstract LocalDateTime endTime();
}
