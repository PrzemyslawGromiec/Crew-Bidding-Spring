package com.bidding.crew.event;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int priority;
    private String description;
    private boolean reoccurring = false;

    public Event() {
    }

    public Event(LocalDateTime startTime, LocalDateTime endTime, int priority, String description) {
        this(startTime,endTime,priority,description,false);
    }

    public Event(LocalDateTime startTime, LocalDateTime endTime, int priority, String description, boolean reoccurring) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.description = description;
        this.reoccurring = reoccurring;
    }

    public Event(EventDto eventDto) {
        startTime = eventDto.getStartTime();
        endTime = eventDto.getEndTime();
        priority = eventDto.getPriority();
        description = eventDto.getDescription();
        reoccurring = eventDto.isReoccurring();
    }

    public EventDto toDto() {
        return new EventDto(startTime,endTime,priority,description, reoccurring);
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

    public int currentMonth() {
        return startTime.getMonthValue();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", reoccurring=" + reoccurring +
                '}';
    }
}
