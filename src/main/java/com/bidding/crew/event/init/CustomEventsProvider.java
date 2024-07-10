package com.bidding.crew.event.init;

import com.bidding.crew.event.EventDto;
import com.bidding.crew.general.Time;

import java.time.LocalDateTime;
import java.util.List;

public class CustomEventsProvider implements TestEventSource {
    @Override
    public List<EventDto> createEvents() {
        return createMyEvents();
    }

    private List<EventDto> createMyEvents() {
        LocalDateTime startOfNextMonth = Time.getTime().startOfNextMonthDate();
        LocalDateTime startOfEvent1 = startOfNextMonth.plusDays(2).plusHours(3).plusMinutes(42);
        LocalDateTime endOfEvent1 = startOfEvent1.plusDays(3).plusHours(2).plusMinutes(15);

        EventDto eventDto1 = new EventDto(startOfEvent1, endOfEvent1, 2, "event 1", true);
        EventDto eventDto2 = new EventDto(endOfEvent1.plusDays(3), endOfEvent1.plusDays(4), 1, "event 2", true);
        EventDto eventDto3 = new EventDto(endOfEvent1.plusDays(7), endOfEvent1.plusDays(10), 3, "event 3", false);
        EventDto eventDto4 = new EventDto(endOfEvent1.plusDays(15), endOfEvent1.plusDays(16), 3, "event4", false);
        EventDto eventDto5 = new EventDto(endOfEvent1.plusDays(21), endOfEvent1.plusDays(23), 1, "event 5", false);
        /*
         * last event times:
         * "startDate": "2024-08-27T23:23:06.921151"
         * "endTime": "2024-08-29T23:23:06.921151"
         * */

        return List.of(eventDto1, eventDto2, eventDto3, eventDto4, eventDto5);
    }
}
