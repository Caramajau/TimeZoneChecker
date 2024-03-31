package org.caramajau.model;

public class TimeFormatConverter {
    private TimeFormatConverter() {}

    public static String convertToValidFormat(String selectedTimeString) throws IllegalArgumentException {
        String timeNumbersString = findTimeNumbersInString(selectedTimeString);
        return createValidFormat(timeNumbersString);

    }

    private static String findTimeNumbersInString(String selectedTimeString) {
        StringBuilder timeNumbersBuilder = new StringBuilder();
        for (char c : selectedTimeString.toCharArray()) {
            if (Character.isDigit(c)) {
                timeNumbersBuilder.append(c);
                if (timeNumbersBuilder.length() == 4) {
                    break;
                }
            }
        }
        return timeNumbersBuilder.toString();
    }

    private static String createValidFormat(String timeNumbersString) throws IllegalArgumentException {
        return switch (timeNumbersString.length()) {
            case 2 -> timeNumbersString + ":00";
            case 4 -> timeNumbersString.substring(0, 2) + ":" + timeNumbersString.substring(2, 4);
            default -> throw new IllegalArgumentException("Invalid time format");
        };
    }
}
