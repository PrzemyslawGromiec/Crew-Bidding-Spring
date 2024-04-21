package com.bidding.crew.event;

import java.time.LocalDateTime;

public class EventDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private int priority;
    private String description;
    private boolean reoccurring = false;

    public EventDto() {
    }

    public EventDto(LocalDateTime start, LocalDateTime end, int priority, String description, boolean reoccurring) {
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.description = description;
        this.reoccurring = reoccurring;
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

    public boolean isReoccurring() {
        return reoccurring;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "start=" + start +
                ", end=" + end +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", reoccurring=" + reoccurring +
                '}';
    }
}
