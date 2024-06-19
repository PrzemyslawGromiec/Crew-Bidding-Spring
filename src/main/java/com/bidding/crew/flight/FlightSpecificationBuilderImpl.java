package com.bidding.crew.flight;

import org.springframework.data.jpa.domain.Specification;

public class FlightSpecificationBuilderImpl implements FlightSpecificationBuilder{
    @Override
    public Specification<Flight> getSpecificationFor(FlightSpecificationInput specificationInput) {
        return null;
    }
}
