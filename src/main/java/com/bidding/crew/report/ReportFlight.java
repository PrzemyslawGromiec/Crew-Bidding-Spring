package com.bidding.crew.report;

import com.bidding.crew.flight.Flight;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ReportFlight extends Request {
    @OneToOne
    private Flight flight;

    public ReportFlight() {
    }

    public ReportFlight(Flight flight, int stars) {
        super(stars);
        this.flight = flight;
    }

    public ReportFlightResponse toDto() {
        return new ReportFlightResponse(flight.toDto(),getStars(),getPoints());
    }

    @Override
    public LocalDateTime startTime() {
        return flight.getReportTime();
    }

    @Override
    public LocalDateTime endTime() {
        return flight.getClearTime();
    }

    @Override
    public LocalDateTime endTimeBuffered() {
        return flight.getClearTimeWithBuffer();
    }

    @Override
    public LocalDate startDate() {
        return flight.getReportTime().toLocalDate();
    }


    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return "FlightRequest{" +
                "flight=" + flight +
                '}';
    }
}
