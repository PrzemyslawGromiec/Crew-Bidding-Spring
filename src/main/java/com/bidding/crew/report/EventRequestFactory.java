package com.bidding.crew.report;

import com.bidding.crew.event.EventDto;
import com.bidding.crew.general.Time;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventRequestFactory {
    private List<EventRequest> requests = new ArrayList<>();
    private Time time;

    public EventRequestFactory(Time time) {
        this.time = time;
    }

    public List<EventRequest> createRequests(List<EventDto> eventDtos) {
        List<EventDto> sortedAndFilteredEvents = eventDtos.stream()
                .filter(
                        eventDto -> eventDto.getStartTime().getMonth() == time.nextMonthLocalDate().getMonth()
                        || eventDto.getEndTime().getMonth() == time.nextMonthLocalDate().getMonth()
                ).sorted(Comparator.comparing(EventDto::getStartTime)).toList();

        List<List<EventDto>> groupedEvents = new ArrayList<>();

        for (EventDto eventDto : sortedAndFilteredEvents) {
            if (groupedEvents.isEmpty()) {
                groupedEvents.add(new ArrayList<>());
                groupedEvents.getFirst().add(eventDto);
                continue;
            }
            List<EventDto> lastGroup = groupedEvents.getLast();
            EventDto lastEventInGroup = lastGroup.getLast();

            if (eventDto.getPriority() == lastEventInGroup.getPriority() &&
            !eventDto.getStartTime().toLocalDate().isAfter(lastEventInGroup.getEndTime().toLocalDate().plusDays(1))) {
                lastGroup.add(eventDto);
                continue;
            }
            List<EventDto> newGroup = new ArrayList<>();
            newGroup.add(eventDto);
            groupedEvents.add(newGroup);
        }

        for(List<EventDto> group : groupedEvents) {
            requests.add(new EventRequest(group));
        }
        return requests;
    }
}
