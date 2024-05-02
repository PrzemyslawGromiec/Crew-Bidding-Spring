package com.bidding.crew.event;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventSearchDao {

    public List<Event> findAllBySimpleQuery(int priority, boolean reoccurring){
        return null;
    };
}
