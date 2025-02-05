package com.bidding.crew.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightSpecificationInput {

    private String airportCode;
    private String flightNumber;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private AircraftType aircraftType;



    @Override
    public String toString() {
        return "FlightSpecificationInput{" +
                "airportCode='" + airportCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", reportTime=" + reportTime +
                ", clearTime=" + clearTime +
                ", aircraftType=" + aircraftType +
                '}';
    }
}
