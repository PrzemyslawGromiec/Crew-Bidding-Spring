package com.bidding.crew.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportEventDto {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int stars;
}
