package com.bidding.crew.flight;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FlightSpecificationBuilderImpl implements FlightSpecificationBuilder{
    @Override
    public Specification<Flight> getSpecificationFor(FlightSpecificationInput specificationInput) {

        AircraftType type = specificationInput.getAircraftType();
        return getAircraftTypeSpec(type);
    }

    private Specification<Flight> getAircraftTypeSpec(AircraftType type) {
        return ((root, query, cb) -> cb.equal(root.get("aircraftType"), type));
    }
}
