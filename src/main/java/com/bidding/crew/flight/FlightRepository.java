package com.bidding.crew.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer>, JpaSpecificationExecutor<Flight> {
    @Query("SELECT f FROM Flight f WHERE f.airportCode = :airportCode")
    List<Flight> findFlightsByAirport(String airportCode);

}
