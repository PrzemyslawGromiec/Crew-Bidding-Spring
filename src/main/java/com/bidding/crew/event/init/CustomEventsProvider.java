package com.bidding.crew.event.init;

import com.bidding.crew.event.EventDto;
import com.bidding.crew.general.Time;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
public class CustomEventsProvider implements TestEventSource {
    @Override
    public List<EventDto> createEvents() {
        return createMyEvents();
    }

    private List<EventDto> createMyEvents() {
        LocalDateTime startOfNextMonth = Time.getTime().nextMonthTime();
        LocalDateTime startOfEvent1 = startOfNextMonth.plusDays(3).plusHours(3).plusMinutes(42);

        EventDto eventDto1 = new EventDto(startOfEvent1, startOfEvent1.plusDays(2),2, "event 1", true);
        EventDto eventDto2 = new EventDto(startOfEvent1.plusDays(3), startOfEvent1.plusDays(6),1, "event 2", true);
        EventDto eventDto3 = new EventDto(startOfEvent1.plusDays(6), startOfEvent1.plusDays(9),3, "event 3", false);
        EventDto eventDto6 = new EventDto(startOfEvent1.plusDays(6), startOfEvent1.plusDays(10),3, "event6", false);
        EventDto eventDto4 = new EventDto(startOfEvent1.plusDays(9), startOfEvent1.plusDays(13),1, "event 4", false);
        EventDto eventDto5 = new EventDto(startOfEvent1.plusDays(15), startOfEvent1.plusDays(20),1, "event 5", true);
        //EventDto eventDto7 = new EventDto(startOfEvent1.plusDays(15), startOfEvent1.plusDays(19),1, "event 7", true);

        return List.of(eventDto1,eventDto2,eventDto3,eventDto4,eventDto5, eventDto6);
    }
}
