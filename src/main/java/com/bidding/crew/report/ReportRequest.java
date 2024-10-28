package com.bidding.crew.report;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportRequest {

    @NotNull(message = "Id cannot be null")
    private Long id;
    private List<RequestDto> requests;

    public ReportRequest() {
    }

}
