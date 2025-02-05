package com.bidding.crew.flight;

import com.bidding.crew.general.Time;
import com.bidding.crew.report.SuggestionCriteriaDto;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class FlightSpecificationBuilderImpl implements FlightSpecificationBuilder {
    @Override
    public Specification<Flight> getSpecificationFor(FlightSpecificationInput specificationInput) {

        if (specificationInput.getReportTime() == null) {
            specificationInput.setReportTime(Time.getTime().startOfNextMonthDate());
        }
        if (specificationInput.getClearTime() == null) {
            specificationInput.setClearTime(Time.getTime().endOfNextMonthDate());
        }

        Specification<Flight> spec = Specification.where(null);

        if (specificationInput.getAirportCode() != null) {
            spec = spec.and(getAirportCodeSpec(specificationInput.getAirportCode()));
        }

        if (specificationInput.getFlightNumber() != null) {
            spec = spec.and(getFlightNumSpec(specificationInput.getFlightNumber()));
        }

        if (specificationInput.getAircraftType() != null) {
            spec = spec.and(getAircraftTypeSpec(specificationInput.getAircraftType()));
        }

        if (specificationInput.getReportTime() != null) {
            spec = spec.and(getFlightStaringAfter(specificationInput.getReportTime()));
        }

        if (specificationInput.getClearTime() != null) {
            spec = spec.and(getFlightEndingBefore(specificationInput.getClearTime()));
        }

        return spec;
    }

    @Override
    public Specification<Flight> getSuggestedFlightsWithSpecification(SuggestionCriteriaDto criteria) {

        Specification<Flight> spec = Specification.where(null);

        if (criteria.getReportTime() != null) {
            spec = spec.and(getFlightStaringAfter(criteria.getReportTime()));
        }

        if (criteria.getClearTime() != null) {
            spec = spec.and(getFlightEndingBefore(criteria.getClearTime()));
        }

        if (criteria.getMinDuration() != null) {
            spec = spec.and(getFlightDuration(criteria.getMinDuration()));
        }

        if (criteria.getAircraftType() != null) {
            spec = spec.and(doesNotHaveAircraftType(criteria.getAircraftType()));
        }

        return spec;
    }


    private Specification<Flight> getAirportCodeSpec(String airport) {
        return (root, query, cb) -> cb.equal(root.get("airportCode"), airport);
    }

    private Specification<Flight> getFlightNumSpec(String flightNum) {
        return (root, query, cb) -> cb.equal(root.get("flightNumber"), flightNum);
    }

    private Specification<Flight> getAircraftTypeSpec(AircraftType type) {
        return (root, query, cb) -> cb.equal(root.get("aircraftType"), type);
    }

    private Specification<Flight> getFlightEndingBefore(LocalDateTime date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("clearTime"), date);
    }

    private Specification<Flight> getFlightStaringAfter(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("reportTime"), date);
    }

    //todo: baza danych H2 ma problem z funkcja sql timestampdiff
    private Specification<Flight> getFlightDuration(Duration minDuration) {
        return (root, query, cb) -> {
            Expression<Long> durationInSeconds = cb.function(
                    "TIMESTAMPDIFF",
                    Long.class,
                    cb.literal(ChronoUnit.SECONDS.toString()),
                    root.get("reportTime"),
                    root.get("clearTime")
            );

            return cb.greaterThanOrEqualTo(durationInSeconds, minDuration.getSeconds());
        };
    }

    private Specification<Flight> doesNotHaveAircraftType(AircraftType aircraftType) {
        return (root, query, cb) -> cb.notEqual(root.get("aircraftType"), aircraftType);
    }
}
