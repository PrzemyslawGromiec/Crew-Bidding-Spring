package com.bidding.crew.event;

import org.springframework.data.jpa.domain.Specification;

public interface EventSpecificationBuilder {
    Specification<Event> getSpecificationFor(SpecificationInput2 specificationInput2);
}
