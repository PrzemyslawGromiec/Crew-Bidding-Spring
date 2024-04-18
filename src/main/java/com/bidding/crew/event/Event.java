package com.bidding.crew.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    private int priority;
    private String description;
    private boolean reoccuring = false;

    public Event() {
    }

    public Event(LocalDateTime start, LocalDateTime end, int priority, String description) {
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }
}
