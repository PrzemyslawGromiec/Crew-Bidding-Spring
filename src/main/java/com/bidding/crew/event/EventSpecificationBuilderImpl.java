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

        Integer priority = input.getPriority();
        Specification<Event> priorityFilter = isNull(priority).or(getPrioritySpec(priority));
        return Specification.allOf(descriptionFilter,timeFilter,reoccurringFilter,priorityFilter);
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

    private Specification<Event> getPrioritySpec(Integer priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority);
    }

    private Specification<Event> isNull(Object value) {
       return  (root, query, cb) -> cb.isNull(cb.literal(value));
    }

    //todo: daty (przed wybrana data, wyszukiwanie po miesiacu), priority

}

/*
* pakiet schedule
* post schedule - tworzy nowy schedule -> trafiają do niego od razu eventy z danego miesiąca (jako EventRequest)
* można dodać wybrany/ne flighty do schedule (trafiają do niego jako FlightRequest)
* pobierz flghty pogrupowane w periody
*
*
*
*
*
* */
