package com.bidding.crew.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer>, JpaSpecificationExecutor<Flight> {
    @Query("SELECT f FROM Flight f WHERE f.airportCode = :airportCode")
    List<Flight> findFlightsByAirport(String airportCode);

    //todo refactor
    @Query("SELECT f FROM Flight f WHERE f.reportTime >= :startTime AND f.clearTime <= :endTime AND f.aircraftType <> :aircraftType")
    List<Flight> findFlightsWithinPeriodExcludingOneType(@Param("startTime") LocalDateTime startTime,
                                                         @Param("endTime") LocalDateTime endTime,
                                                         @Param("aircraftType") AircraftType aircraftType);

    //list of excluded aircraft types
    @Query("SELECT f FROM Flight f WHERE f.reportTime >= :startTime AND f.clearTime <= :endTime AND f.aircraftType NOT IN :aircraftTypes")
    List<Flight> findFlightsWithinPeriodExcludingTypes(@Param("startTime") LocalDateTime startTime,
                                                       @Param("endTime") LocalDateTime endTime,
                                                       @Param("aircraftTypes") List<AircraftType> aircraftTypes);


}
