package org.caramajau.model.timezonehandling;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class DateHandler {
    private String selectedTimeZone = TimeZoneHandler.getNoSelectedTimeZoneString();
    private LocalTime selectedTime = TimeZoneHandler.getDefaultTime();
    private LocalDate selectedDate = TimeZoneHandler.getDefaultDate();

    public void setSelectedTimeZone(TimeZoneOffsets selectedTimeZone) {
        this.selectedTimeZone = TimeZoneHandler.getTimeZoneBasedOnOffset(selectedTimeZone);
    }

    public int getSelectedHour() {
        return selectedTime.getHour();
    }

    public void setSelectedHour(int selectedHour) {
        selectedTime = LocalTime.of(selectedHour, selectedTime.getMinute());
    }

    public int getSelectedMinute() {
        return selectedTime.getMinute();
    }

    public void setSelectedMinute(int selectedMinute) {
        selectedTime = LocalTime.of(selectedTime.getHour(), selectedMinute);
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public boolean noTimeZoneSelected() {
        return selectedTimeZone.equals(TimeZoneHandler.getNoSelectedTimeZoneString());
    }

    public ZonedDateTime getCompleteSelectedDate() {
        return TimeZoneHandler.convertToCurrentTimeZone(selectedDate, selectedTime, selectedTimeZone);
    }
}
