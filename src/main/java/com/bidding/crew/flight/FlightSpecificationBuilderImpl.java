package com.bidding.crew.flight;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FlightSpecificationBuilderImpl implements FlightSpecificationBuilder {
    @Override
    public Specification<Flight> getSpecificationFor(FlightSpecificationInput specificationInput) {

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

        if (specificationInput.getClearTime() != null) {
            spec = spec.and(getFlightEndingBefore(specificationInput.getClearTime()));
        }

        System.out.println(specificationInput);

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
}
