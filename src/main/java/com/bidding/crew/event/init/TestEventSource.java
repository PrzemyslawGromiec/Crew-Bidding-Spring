package com.bidding.crew.event.init;

import com.bidding.crew.event.EventDto;

import java.util.List;

public interface TestEventSource {
    List<EventDto> createEvents();
}
