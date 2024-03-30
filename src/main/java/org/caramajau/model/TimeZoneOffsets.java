package org.caramajau.model;

public enum TimeZoneOffsets {
    // Some time zone abbreviations with their offsets
    // Based on https://en.wikipedia.org/wiki/List_of_time_zone_abbreviations
    // Ordered by offset and only integer offsets are considered

    // American time zones
    PST(-8),
    PDT(-7),
    CST(-6),
    CDT(-5),
    EST(-5),
    EDT(-4),

    // European time zones
    GMT(0),
    UTC(0),
    BST(1),
    CET(1),
    CEST(2),
    EET(2),
    EEST(3),

    // Asian time zone(s)
    JST(9);

    private final int offset;
    private TimeZoneOffsets(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return name() + " (" + offset + ")";
    }

    public static TimeZoneOffsets fromString(String str) {
        for (TimeZoneOffsets timeZone : TimeZoneOffsets.values()) {
            if (timeZone.toString().equals(str)) {
                return timeZone;
            }
        }
        throw new IllegalArgumentException("No enum constant " + TimeZoneOffsets.class.getCanonicalName() + " with toString() " + str);
    }
}
