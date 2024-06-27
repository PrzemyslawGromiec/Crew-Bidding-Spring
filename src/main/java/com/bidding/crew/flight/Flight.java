package com.bidding.crew.flight;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String airportCode;
    private String flightNumber;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private AircraftType aircraftType;


    public Flight() {
    }

    public Flight(FlightDto flightDto) {
        flightNumber = flightDto.getFlightNumber();
        airportCode = flightDto.getAirportCode();
        reportTime = flightDto.getReportTime();
        clearTime = flightDto.getClearTime();
        aircraftType = flightDto.getAircraftType();
    }

    public FlightDto toDto() {
        return new FlightDto(airportCode,flightNumber,reportTime,clearTime,aircraftType);
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public LocalDateTime getClearTime() {
        return clearTime;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }
}


/*
* Flight + Events w bazie
*  FlightRequesty na bazie Flight trafiaja do FlightRequestFactory
* EventRequesty na bazie eventów trafiają do EventRequestFactory
* potem na bazie jednych i drugich przygotowywane są periody w locie
* periody są uzupełniane lotami
* loty są prezentowane
* Można wybrać lot lub pominąć
* Nowy lot jest przerabiany na FlightRequest i dodawany do puli w factory
* Na tej bazie powstaje kolejny period do wyboru aż się skończą
* Wynikowo mamy zbiór Fliht i Event requestów w raporcie
*
* createRaport -> zwraca raport w budowie
*getPeriod/raportId -> zwraca liste lotów na najbliszy period
* postDecision -> decyzja zapisana
*
* postAction (stworz raport) -> pierwszy period
* postAction (decyzja) -> kolejny period
*
*
* */



