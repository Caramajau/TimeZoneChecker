package org.caramajau.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Set;

public class TimeZoneHandler {
    private ZonedDateTime currentDateTime;

    private void setCurrentDateTime() {
        currentDateTime = ZonedDateTime.now(ZoneId.systemDefault());
    }

    public static Set<String> getAllTimeZones() {
        return ZoneId.getAvailableZoneIds();
    }

    public static Order compareTimeZone(String zoneString1, String zoneString2) {
        Instant currentTime = Instant.now();

        ZoneOffset offset1 = getZoneOffset(currentTime, zoneString1);
        ZoneOffset offset2 = getZoneOffset(currentTime, zoneString2);

        if (offset1.compareTo(offset2) > 0) {
            return Order.AFTER;
        } else if (offset1.compareTo(offset2) < 0) {
            return Order.BEFORE;
        } else {
            return Order.SAME;
        }
    }

    private static ZoneOffset getZoneOffset(Instant currentTime, String zoneString) {
        return ZoneId.of(zoneString).getRules().getOffset(currentTime);
    }

}
