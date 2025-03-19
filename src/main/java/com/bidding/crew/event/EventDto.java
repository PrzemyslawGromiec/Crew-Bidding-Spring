package com.bidding.crew.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int priority;
    private String description;
    private boolean reoccurring = false;

    public Event toEntity() {
        return new Event(startTime,endTime,priority,description, reoccurring);
    }
}
