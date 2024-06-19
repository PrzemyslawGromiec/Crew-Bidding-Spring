package com.bidding.crew.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationInput {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean reoccurring;
    private String description;
    private Integer priority;

    @Override
    public String toString() {
        return "SpecificationInput{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", reoccurring=" + reoccurring +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}
