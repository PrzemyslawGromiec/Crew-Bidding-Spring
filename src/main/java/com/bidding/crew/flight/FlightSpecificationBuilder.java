package com.bidding.crew.flight;

import com.bidding.crew.report.SuggestionCriteriaDto;
import org.springframework.data.jpa.domain.Specification;

public interface FlightSpecificationBuilder {
    Specification<Flight> getSpecificationFor(FlightSpecificationInput specificationInput);
    Specification<Flight> getSuggestedFlightsWithSpecification(SuggestionCriteriaDto suggestionCriteriaDto);
}
