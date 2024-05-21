package org.caramajau.model.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDigitInStringChecker {
    private static final String VALID_STRING_WITH_DIGITS = "1234567890";
    private static final String INVALID_STRING_WITH_DIGITS = "1234567890a";
    private static final String INVALID_STRING_WITHOUT_DIGITS = "abcdefghij";
    private static final String EMPTY_STRING = "";

    @Test
    void testIsAllDigitGivenValidStringReturnsTrue() {
        assertTrue(DigitInStringChecker.isAllDigit(VALID_STRING_WITH_DIGITS));
    }

    @Test
    void testIsAllDigitGivenInvalidStringReturnsFalse() {
        assertFalse(DigitInStringChecker.isAllDigit(INVALID_STRING_WITH_DIGITS));
    }

    @Test
    void testIsAllDigitGivenInvalidStringWithoutDigitsReturnsFalse() {
        assertFalse(DigitInStringChecker.isAllDigit(INVALID_STRING_WITHOUT_DIGITS));
    }

    @Test
    void testIsAllDigitGivenEmptyStringReturnsTrue() {
        assertTrue(DigitInStringChecker.isAllDigit(EMPTY_STRING));
    }
}
