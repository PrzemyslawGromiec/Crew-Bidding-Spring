package com.bidding.crew.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationInput {
    private String columnName;
    private String value;
    private List<LocalDateTime> timeValues;

}
