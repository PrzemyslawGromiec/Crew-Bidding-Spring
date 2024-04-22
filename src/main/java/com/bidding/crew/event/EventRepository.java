package com.bidding.crew.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByReoccurring(boolean reoccurring);

    @Query("SELECT e FROM Event e WHERE e.startTime < :startDate")
    List<Event> findEventStartingBeforeDate(@Param("startDate") LocalDateTime startDate);

}
