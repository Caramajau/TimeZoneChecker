package org.caramajau.model.utility;

public class DigitInStringChecker {
    private DigitInStringChecker() {}

    public static boolean isAllDigit(String stringToTest) {
        for (char c : stringToTest.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
