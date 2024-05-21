package org.caramajau.model.timezonehandling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
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

    static Stream<LocalDate> validNonLeapYearDateProvider() {
        // 2021 is a non-leap year
        int year = 2021;
        // Stream that starts from 1 January
        return Stream.iterate(LocalDate.of(year, Month.JANUARY, 1),
                        // increment it by 1 day
                        date -> date.plusDays(1))
                // limit it to the last day of the year
                .limit(LocalDate.of(year, Month.DECEMBER, 31).getDayOfYear());
    }

    static Stream<LocalDate> validLeapYearDateProvider() {
        // 2020 is a leap year
        int year = 2020;
        return Stream.iterate(LocalDate.of(year, Month.JANUARY, 1),
                        date -> date.plusDays(1))
                .limit(LocalDate.of(year, Month.DECEMBER, 31).getDayOfYear());
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

    // Tests for setSelectedDate
    @Test
    void testSetSelectedDateGivenNoDateSelectedReturnsDefaultDate() {
        assertEquals(TimeZoneHandler.getDefaultDate(), dateHandler.getSelectedDate());
    }

    @Test
    void testSetSelectedDateGivenDateSelectedReturnsSelectedDate() {
        LocalDate testDate = TimeZoneHandler.getDefaultDate().plusDays(1);
        dateHandler.setSelectedDate(testDate);
        assertEquals(testDate, dateHandler.getSelectedDate());
    }

    @Test
    void testSetSelectedDateGivenNullDateThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dateHandler.setSelectedDate(null));
    }

    @ParameterizedTest
    @MethodSource("validNonLeapYearDateProvider")
    void testSetSelectedDateGivenValidNonLeapYearDateReturnsDate(LocalDate date) {
        dateHandler = new DateHandler();
        dateHandler.setSelectedDate(date);
        assertEquals(date, dateHandler.getSelectedDate());
    }

    @ParameterizedTest
    @MethodSource("validLeapYearDateProvider")
    void testSetSelectedDateGivenValidLeapYearDateReturnsDate(LocalDate date) {
        dateHandler = new DateHandler();
        dateHandler.setSelectedDate(date);
        assertEquals(date, dateHandler.getSelectedDate());
    }
}
