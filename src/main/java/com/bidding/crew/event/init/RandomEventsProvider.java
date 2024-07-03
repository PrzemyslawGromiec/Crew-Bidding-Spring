package com.bidding.crew.event.init;

import com.bidding.crew.event.EventDto;
import com.bidding.crew.general.Time;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEventsProvider implements TestEventSource {
    private Random random = new Random();

    @Override
    public List<EventDto> createEvents() {
        List<EventDto> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(createRandomEvent());

        }
        return events;
    }

    private EventDto createRandomEvent() {
        LocalDateTime startOfNextMonth = Time.getTime().nextMonthTime();

        LocalDateTime startTime = startOfNextMonth.plusDays(random.nextInt(startOfNextMonth.toLocalDate().lengthOfMonth()))
                .plusHours(random.nextInt(24))
                .plusMinutes(random.nextInt(60));

        LocalDateTime endTime = startTime.plusDays(random.nextInt(3) + 1).plusHours(random.nextInt(5) + 1)
                .plusMinutes(random.nextInt(60));

        int priority = random.nextInt(3)+1;
        String description = "Event " + (random.nextInt(100) + 1);
        boolean reoccurring = random.nextBoolean();

        return new EventDto(startTime,endTime,priority,description,reoccurring);
    }
}
