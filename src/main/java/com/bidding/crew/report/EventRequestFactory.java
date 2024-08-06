package com.bidding.crew.report;

import com.bidding.crew.event.Event;
import com.bidding.crew.general.Time;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventRequestFactory {
    private List<ReportEvent> requests = new ArrayList<>();
    private Time time;

    public EventRequestFactory(Time time) {
        this.time = time;
    }

    public List<ReportEvent> createRequests(List<Event> events) {
        List<Event> sortedAndFilteredEvents = events.stream()
                .filter(
                        event -> event.getStartTime().getMonth() == time.nextMonthLocalDate().getMonth()
                        || event.getEndTime().getMonth() == time.nextMonthLocalDate().getMonth()
                ).sorted(Comparator.comparing(Event::getStartTime)).toList();

        List<List<Event>> groupedEvents = new ArrayList<>();

        for (Event event : sortedAndFilteredEvents) {
            if (groupedEvents.isEmpty()) {
                groupedEvents.add(new ArrayList<>());
                groupedEvents.getFirst().add(event);
                continue;
            }
            List<Event> lastGroup = groupedEvents.getLast();
            Event lastEventInGroup = lastGroup.getLast();

            if (event.getPriority() == lastEventInGroup.getPriority() &&
            !event.getStartTime().toLocalDate().isAfter(lastEventInGroup.getEndTime().toLocalDate().plusDays(1))) {
                lastGroup.add(event);
                continue;
            }
            List<Event> newGroup = new ArrayList<>();
            newGroup.add(event);
            groupedEvents.add(newGroup);
        }

        for(List<Event> group : groupedEvents) {
            requests.add(new ReportEvent(group));
        }
        return requests;
    }
}
