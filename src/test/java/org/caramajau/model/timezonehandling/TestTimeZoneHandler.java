package org.caramajau.model.timezonehandling;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestTimeZoneHandler {
    // Tests for getNoSelectedTimeZoneString
    @Test
    void testGetNoSelectedTimeZoneStringReturnsNoSelectedTimeZoneString() {
        String noSelectedTimeZoneString = TimeZoneHandler.getNoSelectedTimeZoneString();
        assertEquals("NaN", noSelectedTimeZoneString);
    }

    // Tests for getDefaultTime
    @Test
    void testGetDefaultTimeReturnsMidnight() {
        LocalTime defaultTime = TimeZoneHandler.getDefaultTime();
        assertEquals(LocalTime.of(0, 0), defaultTime);
    }

    // Tests for getAllTimeZoneAbbreviationsAsString
    @Test
    void testGetAllTimeZoneAbbreviationsAsStringReturnsNonEmptyList() {
        List<String> timeZoneAbbreviations = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        assertFalse(timeZoneAbbreviations.isEmpty());
    }
    @Test
    void testEachMemberInAllTimeZoneAbbreviationsAsStringIsNotNull() {
        List<String> timeZoneAbbreviations = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        for (String timeZoneAbbreviation : timeZoneAbbreviations) {
            assertNotNull(timeZoneAbbreviation);
        }
    }
    @Test
    void testEachMemberInAllTimeZoneAbbreviationsAsStringIsNotEmpty() {
        List<String> timeZoneAbbreviations = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        for (String timeZoneAbbreviation : timeZoneAbbreviations) {
            assertFalse(timeZoneAbbreviation.isEmpty());
        }
    }
    @Test
    void testEachMemberInAllTimeZoneAbbreviationsAsStringIsNotWhitespace() {
        List<String> timeZoneAbbreviations = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        for (String timeZoneAbbreviation : timeZoneAbbreviations) {
            assertFalse(timeZoneAbbreviation.isBlank());
        }
    }

    // Tests for getDefaultDate
    @Test
    void testGetDefaultDateReturnsCurrentDate() {
        LocalDate defaultDate = TimeZoneHandler.getDefaultDate();
        assertEquals(LocalDate.now(), defaultDate);
    }

    // Tests for getTimeZoneBasedOnOffset
    @Test
    void testGetTimeZoneBasedOnOffsetGivenNullOffsetThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> TimeZoneHandler.getTimeZoneBasedOnOffset(null));
    }
}
