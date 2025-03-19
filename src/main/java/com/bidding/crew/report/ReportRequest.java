package com.bidding.crew.report;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    @NotNull(message = "Id cannot be null")
    private Long id;
    private List<RequestDto> requests;
}
