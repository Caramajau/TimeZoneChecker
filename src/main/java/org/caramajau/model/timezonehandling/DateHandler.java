package org.caramajau.model.timezonehandling;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateHandler {
    private String selectedTimeZone = TimeZoneHandler.getNoSelectedTimeZoneString();
    LocalTime selectedTime = TimeZoneHandler.getDefaultTime();
    private LocalDate selectedDate = TimeZoneHandler.getDefaultDate();

    public String getSelectedTimeZone() {
        return selectedTimeZone;
    }

    public void setSelectedTimeZone(String selectedTimeZone) {
        this.selectedTimeZone = selectedTimeZone;
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

    public LocalTime getSelectedTime() {
        return selectedTime;
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
}
