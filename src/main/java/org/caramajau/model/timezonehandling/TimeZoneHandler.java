package org.caramajau.model.timezonehandling;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TimeZoneHandler {
    private static final List<String> AllTimeZones = createAllTimeZones();
    private static final List<TimeZoneOffsets> AllTimeZonesAbbreviations = createAllTimeZoneAbbreviations();
    private static final List<String> AllTimeZonesAbbreviationsAsString = createAllTimeZoneAbbreviationsAsString();

    private static final String NO_SELECTED_TIME_ZONE_STRING = "NaN";
    private static final LocalTime defaultTime = LocalTime.of(0,0);
    private static final LocalDate defaultDate = LocalDate.now();

    private TimeZoneHandler() {}

    static String getTimeZoneBasedOnOffset(TimeZoneOffsets offset) {
        if (offset == null) {
            throw new IllegalArgumentException("No time zone selected since offset is null");
        }
        for (String timeZone : AllTimeZones) {
            ZoneOffset zoneOffset = getZoneOffset(Instant.now(), timeZone);
            if (zoneOffset.getTotalSeconds() == offset.getOffset() * 3600) {
                return timeZone;
            }
        }
        throw new IllegalArgumentException("No time zone found with offset " + offset.getOffset());
    }

    private static ZoneOffset getZoneOffset(Instant currentTime, String zoneString) {
        return ZoneId.of(zoneString).getRules().getOffset(currentTime);
    }

    static ZonedDateTime convertToCurrentTimeZone(LocalDate inputDate, LocalTime inputTime, String inputTimeZone) {
        if (inputTimeZone.equals(NO_SELECTED_TIME_ZONE_STRING)) {
            throw new IllegalArgumentException("No time zone selected since inputTimeZone is " + inputTimeZone);
        }

        ZoneId inputTimeZoneId = ZoneId.of(inputTimeZone);

        // Combine the input date and time into a ZonedDateTime object
        ZonedDateTime inputDateTime = ZonedDateTime.of(inputDate, inputTime, inputTimeZoneId);

        // Get the current time zone
        ZoneId currentZone = ZoneId.systemDefault();

        // Convert the input date to the current time zone
        return inputDateTime.withZoneSameInstant(currentZone);
    }

    private static List<String> createAllTimeZones() {
        Set<String> allTimeZonesSet = ZoneId.getAvailableZoneIds();
        ArrayList<String> allTimeZonesList = new ArrayList<>(allTimeZonesSet);
        allTimeZonesList.sort(String::compareTo);
        return allTimeZonesList;
    }

    private static List<TimeZoneOffsets> createAllTimeZoneAbbreviations() {
        TimeZoneOffsets[] enumArray = TimeZoneOffsets.values();
        return new ArrayList<>(List.of(enumArray));
    }

    private static List<String> createAllTimeZoneAbbreviationsAsString() {
        List<String> allTimeZonesAbbreviationsAsStrings = new ArrayList<>();
        for (TimeZoneOffsets timeZone : AllTimeZonesAbbreviations) {
            allTimeZonesAbbreviationsAsStrings.add(timeZone.toString());
        }
        return allTimeZonesAbbreviationsAsStrings;
    }

    public static List<String> getAllTimeZoneAbbreviationsAsString() {
        return new ArrayList<>(AllTimeZonesAbbreviationsAsString);
    }

    public static String getNoSelectedTimeZoneString() {
        return NO_SELECTED_TIME_ZONE_STRING;
    }

    public static LocalTime getDefaultTime() {
        return defaultTime;
    }

    public static LocalDate getDefaultDate() {
        return defaultDate;
    }
}
