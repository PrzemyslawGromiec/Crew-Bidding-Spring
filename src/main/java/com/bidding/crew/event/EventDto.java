package com.bidding.crew.event;

import java.time.LocalDateTime;

public class EventDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int priority;
    private String description;
    private boolean reoccurring = false;

    public EventDto() {
    }

    public EventDto(LocalDateTime startTime, LocalDateTime endTime, int priority, String description, boolean reoccurring) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.description = description;
        this.reoccurring = reoccurring;
    }

    public Event toEntity() {
        return new Event(startTime,endTime,priority,description, reoccurring);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
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
                "start=" + startTime +
                ", end=" + endTime +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", reoccurring=" + reoccurring +
                '}';
    }
}
