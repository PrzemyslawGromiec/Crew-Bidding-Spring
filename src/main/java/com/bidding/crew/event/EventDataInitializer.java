package com.bidding.crew.event;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class EventDataInitializer implements CommandLineRunner {

    private final Random random = new Random();
    private final EventService eventService;

    public EventDataInitializer(EventService eventService) {
        this.eventService = eventService;
    }


    @Override
    public void run(String... args) throws Exception {
        List<EventDto> events = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            events.add(createRandomEvent());
        }

        events.forEach(eventService::saveEvent);
        events.forEach(event -> System.out.println(event.getDescription() + " starts at " + event.getStartTime()));
    }

    private EventDto createRandomEvent() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNextMonth = now.withDayOfMonth(1).plusMonths(1);

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
