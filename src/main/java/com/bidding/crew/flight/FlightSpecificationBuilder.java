package com.bidding.crew.flight;

import org.springframework.data.jpa.domain.Specification;

public interface FlightSpecificationBuilder {
    Specification<Flight> getSpecificationFor(FlightSpecificationInput specificationInput);
}
