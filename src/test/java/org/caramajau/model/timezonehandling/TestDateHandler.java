package org.caramajau.model.timezonehandling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.ZonedDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestDateHandler {
    private DateHandler dateHandler;

    @BeforeEach
    void setUp() {
        dateHandler = new DateHandler();
    }

    static Stream<Integer> validHourProvider() {
        return IntStream.rangeClosed(0, 23).boxed();
    }

    static Stream<Integer> validMinuteProvider() {
        return IntStream.rangeClosed(0, 59).boxed();
    }

    // Tests for noTimeZoneSelected
    @Test
    void testNoTimeZoneSelectedGivenNoTimeZoneSelectedReturnsTrue() {
        assertTrue(dateHandler.noTimeZoneSelected());
    }

    // Tests for setSelectedTimeZone
    @Test
    void testSetSelectedTimeZoneGivenTimeZoneNoTimeZoneSelectedReturnsFalse() {
        dateHandler.setSelectedTimeZone(TimeZoneOffsets.UTC);
        assertFalse(dateHandler.noTimeZoneSelected());
    }

    // Tests for getCompleteSelectedDate
    @Test
    void testGetCompleteSelectedDateGivenNoTimeZoneSelectedThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dateHandler.getCompleteSelectedDate());
    }

    @Test
    void testGetCompleteSelectedDateGivenTimeZoneSelectedReturnsZonedDateTime() {
        dateHandler.setSelectedTimeZone(TimeZoneOffsets.UTC);
        assertNotNull(dateHandler.getCompleteSelectedDate());
    }

    @Test
    void testGetCompleteSelectedDateGivenDifferentTimeZoneSelectedReturnsDifferentZonedDateTime() {
        dateHandler.setSelectedTimeZone(TimeZoneOffsets.UTC);
        ZonedDateTime firstZonedDateTime = dateHandler.getCompleteSelectedDate();

        dateHandler.setSelectedTimeZone(TimeZoneOffsets.CEST);
        ZonedDateTime secondZonedDateTime = dateHandler.getCompleteSelectedDate();

        assertNotEquals(firstZonedDateTime, secondZonedDateTime);
    }

    // Tests for setSelectedHour
    @ParameterizedTest
    @MethodSource("validHourProvider")
    void testSetSelectedHourGivenValidHourReturnsHour(int hour) {
        dateHandler.setSelectedHour(hour);
        assertEquals(hour, dateHandler.getSelectedHour());
    }

    @Test
    void testSetSelectedHourGivenNegativeHourThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dateHandler.setSelectedHour(-1));
    }

    @Test
    void testSetSelectedHourGivenHourGreaterThanMaxHourThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dateHandler.setSelectedHour(24));
    }

    // Tests for setSelectedMinute
    @ParameterizedTest
    @MethodSource("validMinuteProvider")
    void testSetSelectedMinuteGivenValidMinuteReturnsMinute(int minute) {
        dateHandler.setSelectedMinute(minute);
        assertEquals(minute, dateHandler.getSelectedMinute());
    }

    @Test
    void testSetSelectedMinuteGivenNegativeMinuteThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dateHandler.setSelectedMinute(-1));
    }

    @Test
    void testSetSelectedMinuteGivenMinuteGreaterThanMaxMinuteThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dateHandler.setSelectedMinute(60));
    }
}
