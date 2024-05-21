package org.caramajau.model.timezonehandling;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestTimeZoneOffsets {

    @ParameterizedTest
    @EnumSource(TimeZoneOffsets.class)
    void testForEachTimeZoneOffsetFromStringGivenToStringReturnsOriginalTimeZone(TimeZoneOffsets timeZone) {
        String timeZoneString = timeZone.toString();
        TimeZoneOffsets result = TimeZoneOffsets.fromString(timeZoneString);
        assertEquals(timeZone, result);
    }

    @ParameterizedTest
    @EnumSource(TimeZoneOffsets.class)
    void testForEachTimeZoneOffsetGetOffsetReturnsGreaterOrEqualToMinimumNegativeOffset(TimeZoneOffsets timeZone) {
        int timeZoneOffset = timeZone.getOffset();
        assertTrue(timeZoneOffset >= -12);
    }

    @ParameterizedTest
    @EnumSource(TimeZoneOffsets.class)
    void testForEachTimeZoneOffsetGetOffsetReturnsLessOrEqualToMaximumPositiveOffset(TimeZoneOffsets timeZone) {
        int timeZoneOffset = timeZone.getOffset();
        assertTrue(timeZoneOffset <= 14);
    }

    @ParameterizedTest
    @EnumSource(TimeZoneOffsets.class)
    void testForEachTimeZoneOffsetToStringReturnsTimeZoneNameAndOffsetInParentheses(TimeZoneOffsets timeZone) {
        String timeZoneString = timeZone.toString();
        String timeZoneName = timeZone.name();
        int timeZoneOffset = timeZone.getOffset();
        assertEquals(timeZoneName + " (" + timeZoneOffset + ")", timeZoneString);
    }

    @ParameterizedTest
    @EnumSource(TimeZoneOffsets.class)
    void testForEachTimeZoneOffsetFromStringGivenInvalidStringThrowsIllegalArgumentException(TimeZoneOffsets timeZone) {
        String invalidTimeZoneString = "InvalidTimeZoneString";
        assertThrows(IllegalArgumentException.class,
                () -> TimeZoneOffsets.fromString(invalidTimeZoneString));
    }
}
