package com.bidding.crew.report;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public abstract class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //int points = new Random().nextInt();
    int points = generateRandomPoints();

    public abstract LocalDateTime startTime();
    public abstract LocalDateTime endTime();

    public abstract LocalDateTime endTimeBuffered();
    public abstract LocalDate startDate();

    private int generateRandomPoints() {
        int min = 10;
        int max = 200;
        return ThreadLocalRandom.current().nextInt(min/10,max/10 + 1) * 10;
    }
}
