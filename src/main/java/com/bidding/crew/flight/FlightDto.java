package com.bidding.crew.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private int id;
    private String airportCode;
    private String flightNumber;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private AircraftType aircraftType;
}
