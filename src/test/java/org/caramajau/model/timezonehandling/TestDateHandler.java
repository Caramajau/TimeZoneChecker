package org.caramajau.model.timezonehandling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDateHandler {
    private DateHandler dateHandler;

    @BeforeEach
    void setUp() {
        dateHandler = new DateHandler();
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
}
