package com.bidding.crew.flight.generator;

import com.bidding.crew.flight.AircraftType;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import java.util.stream.Collectors;

class StringMapperFlightsTemplateProvider implements FlightsTemplateProvider {

    private TextFileLoader textFileLoader = new TextFileLoader();
    private static final String DEFAULT_FILE_PATH = "Flights.txt";
    private static final Logger logger = LoggerFactory.getLogger(StringMapperFlightsTemplateProvider.class);

    @Override
    public List<FlightTemplate> provideFlights(String... source) {
        String filePath = DEFAULT_FILE_PATH;
        if (source.length == 1) {
            filePath = source[0];
        }
        try {
            List<String> lines = textFileLoader.readFile(filePath);
            return lines.stream()
                    .map(line -> parseLineToFlight(line).orElse(null))
                    .filter(flight -> flight != null)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", filePath, e);
            return new ArrayList<>();
        }
    }

    Optional<FlightTemplate> parseLineToFlight(String line) {
        if (!line.startsWith("✓")) {
            return Optional.empty();
        }
        String hour = line.substring(2, 4);
        int hourInt = Integer.parseInt(hour);
        String minutes = line.substring(5, 7);
        int minutesInt = Integer.parseInt(minutes);
        String flightNr = line.substring(15, 21);
        String code = line.substring(22, 25);

        List<DayOfWeek> daysOfWeek = extractDaysOfWeek(line);
        AircraftType aircraftType = extractAircraftType(line);
        int clearHour = 0;
        int clearMinutes = 0;
        int durationDays = 0;
        if (line.contains("CLEARS")) {
            durationDays = 2;
            String clearTime = line.substring(line.indexOf("@") + 1);
            clearHour = Integer.parseInt(clearTime.substring(0, 2));
            clearMinutes = Integer.parseInt(clearTime.substring(3, 5));

        }

        String[] parts = line.split(" ");
        String numOfDays = "";
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.equalsIgnoreCase("DAY")) {
                numOfDays = parts[i - 1];
                //System.out.println(line);
                durationDays = getNumOfDays(numOfDays);
                break;
            }
        }

        LocalTime clear = LocalTime.of(clearHour, clearMinutes);
        if (durationDays == 0) {
            clear = LocalTime.of(hourInt + 4, minutesInt);
        }

        return Optional.of(new FlightTemplate(flightNr, code, LocalTime.of(hourInt, minutesInt), clear,
                durationDays, daysOfWeek, aircraftType));
    }

    private int getNumOfDays(String days) {
        List<String> listOfNumbers = List.of("TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE");
        int i = listOfNumbers.indexOf(days);
        if (i == -1) {
            throw new UnsupportedOperationException("Not found.");
        }
        return i + 1;
    }

    private List<DayOfWeek> extractDaysOfWeek(String lineFragment) {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        DayOfWeek[] days = DayOfWeek.values();
        for (DayOfWeek day : days) {
            if (lineFragment.contains(day.toString())) {
                daysOfWeek.add(day);
            }
        }

        if (daysOfWeek.isEmpty()) {
            daysOfWeek.addAll(EnumSet.allOf(DayOfWeek.class));
        }
        return daysOfWeek;
    }

    private AircraftType extractAircraftType(String line) {
        int aircraftIndex = line.indexOf("(");
        if (aircraftIndex != -1) {
            String aircraftTypeString = line.substring(aircraftIndex + 1, line.indexOf(")")).trim();
            return AircraftType.valueOf(aircraftTypeString.toUpperCase());
        }
        return AircraftType.A320;
    }
}
