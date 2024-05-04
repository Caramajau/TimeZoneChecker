package org.caramajau.model.timezonehandling;

import java.time.LocalDate;

public class DateHandler {
    private String selectedTimeZone = TimeZoneHandler.getNoSelectedTimeZoneString();
    private int selectedHour = TimeZoneHandler.getDefaultTime().getHour();
    private int selectedMinute = TimeZoneHandler.getDefaultTime().getMinute();
    private LocalDate selectedDate = TimeZoneHandler.getDefaultDate();

    public String getSelectedTimeZone() {
        return selectedTimeZone;
    }

    public void setSelectedTimeZone(String selectedTimeZone) {
        this.selectedTimeZone = selectedTimeZone;
    }

    public int getSelectedHour() {
        return selectedHour;
    }

    public void setSelectedHour(int selectedHour) {
        this.selectedHour = selectedHour;
    }

    public int getSelectedMinute() {
        return selectedMinute;
    }

    public void setSelectedMinute(int selectedMinute) {
        this.selectedMinute = selectedMinute;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }
}
