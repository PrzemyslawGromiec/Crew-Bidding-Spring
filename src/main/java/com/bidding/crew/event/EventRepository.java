package com.bidding.crew.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByReoccurring(boolean reoccurring);

    @Query("SELECT e FROM Event e WHERE e.startTime < :startDate")
    List<Event> findEventStartingBeforeDate(LocalDateTime startDate);

    @Query("SELECT e.description FROM Event e WHERE e.priority = :priority")
    List<String> findEventDescriptionByPriority(int priority);

    @Query("SELECT e from Event e WHERE e.priority = :priority")
    List<Event> findEventsByPriority(int priority);

    @Query("""
            SELECT e FROM Event  e WHERE (:startDate IS NULL OR e.startTime >= :startDate) AND
            (:endDate IS NULL OR e.endTime <= :endDate) AND
            (:reoccurring IS NULL OR e.reoccurring = :reoccurring)""")
    List<Event> findAllByParameters(LocalDateTime startDate, LocalDateTime endDate, Boolean reoccurring);

}
