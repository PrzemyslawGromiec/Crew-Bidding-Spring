package com.bidding.crew.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component

public class EventSpecificationBuilderImpl implements EventSpecificationBuilder {

    @Override
    public Specification<Event> getSpecificationFor(SpecificationInput input) {

        String desc = input.getDescription();
        Specification<Event> descriptionFilter = isNull(desc).or(getDescriptionSpec(desc));

        LocalDateTime time = input.getTime();
        Specification<Event> timeFilter = isNull(time).or(getTimeSpec(time));

        Boolean isReoccurring = input.getReoccurring();
        Specification<Event> reoccurringFilter = isNull(isReoccurring).or(getReoccurringSpec(isReoccurring));
        return Specification.allOf(descriptionFilter,timeFilter,reoccurringFilter);
    }

    private Specification<Event> getTimeSpec(LocalDateTime time) {
        return (root, query, cb) -> cb.between(cb.literal(time), root.get("startTime"), root.get("endTime"));
    }

    private Specification<Event> getDescriptionSpec(String desc) {
        return ((root, query, cb) -> cb.like(root.get("description"),
                "%" + desc + "%"));
    }

    private Specification<Event> getReoccurringSpec(Boolean reoccurring) {
        return ((root, query, cb) -> cb.equal(root.get("reoccurring"), reoccurring));
    }

    private Specification<Event> isNull(Object value) {
       return  (root, query, cb) -> cb.isNull(cb.literal(value));
    }

}
