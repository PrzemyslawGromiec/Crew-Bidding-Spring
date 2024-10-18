package com.bidding.crew.report;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public abstract class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Transient
    int points;
    int stars;

    public Request(int stars) {
        this.stars = stars;
    }

    public Request() {
    }

    public int getStars() {
        return stars;
    }

    public abstract LocalDateTime startTime();
    public abstract LocalDateTime endTime();

    public abstract LocalDateTime endTimeBuffered();
    public abstract LocalDate startDate();

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
