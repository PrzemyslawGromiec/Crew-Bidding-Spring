package com.bidding.crew.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component

public class EventSpecificationBuilderImpl implements EventSpecificationBuilder {

   /* @Override
    public Specification<Event> getSpecificationFor(SpecificationInput input) {
        Specification<Event> spec = Specification.where(null);

        String desc = input.getDescription();
        //Specification<Event> descriptionFilter = isNull(desc).or(getDescriptionSpec(desc));
        Specification<Event> descriptionFilter = isNull("description").or(getDescriptionSpec(desc));

        LocalDateTime time = input.getStartTime();
        //Specification<Event> timeFilter = isNull("time").or(getTimeSpec(time));
        Specification<Event> timeFilter = isNull("startTime").or(getTimeSpec(time));

        Boolean isReoccurring = input.getReoccurring();
        //Specification<Event> reoccurringFilter = isNull("isReoccurring").or(getReoccurringSpec(isReoccurring));
        Specification<Event> reoccurringFilter = isNull("reoccurring").or(getReoccurringSpec(isReoccurring));

        Integer priority = input.getPriority();
        Specification<Event> priorityFilter = isNull("priority").or(getPrioritySpec(priority));

        LocalDateTime endingBeforeTime = input.getEndTime();
        Specification<Event> endingBeforeFilter = isNull("endingBeforeTime").or(getEventsEndingBeforeTime(endingBeforeTime));

        Specification<Event> eventInMonth = null;
        if (input.getStartTime() != null) {
            eventInMonth = isNull("startTime").or(getEventsInChosenMonth(time));
        }


        spec = Specification.anyOf(descriptionFilter,timeFilter,reoccurringFilter,priorityFilter, eventInMonth);
        return spec;

    }*/

    @Override
    public Specification<Event> getSpecificationFor(SpecificationInput input) {
        Specification<Event> spec = Specification.where(null);

        if (input.getStartTime() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("startTime"), input.getStartTime()));
        }

        if (input.getEndTime() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("endTime"), input.getEndTime()));
        }

        if (input.getReoccurring() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("reoccurring"), input.getReoccurring()));
        }

        if (input.getDescription() != null) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("description"), "%" + input.getDescription() + "%"));
        }

        if (input.getPriority() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("priority"), input.getPriority()));
        }

        if (input.getStartTime() != null) {
            spec = spec.and(getEventsInChosenMonth(input.getStartTime()));
        }

        return spec;
    }

    private Specification<Event> getEventsInChosenMonth(LocalDateTime time) {
        return (root, query, cb) -> cb.equal(cb.function("MONTH", Integer.class, root.get("startTime")), time.getMonthValue());
    }



    //szuka eventow gdzie podana godzina i data znajduja sie pomiedzy data poczatku i konca
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

    //szuka eventow ktore koncza sie przed zadanym czasem
    private Specification<Event> getEventsEndingBeforeTime(LocalDateTime time) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("endTime"), time);
    }

   /* private Specification<Event> isNull(Object value) {
       return  (root, query, cb) -> cb.isNull(cb.literal(value));
    }*/

    private Specification<Event> isNull(String attributeName) {
        return (root, query, cb) -> cb.isNull(root.get(attributeName));
    }

}

/*
* pakiet schedule
* post schedule - tworzy nowy schedule -> trafiają do niego od razu eventy z danego miesiąca (jako EventRequest)
* można dodać wybrany/ne flighty do schedule (trafiają do niego jako FlightRequest)
* pobierz flghty pogrupowane w periody
*
*
* */
