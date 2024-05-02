package com.bidding.crew.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationInput {

    private LocalDateTime time;
    private Boolean reoccurring;
    private String description;
    private Integer priority;
}
