package com.sasajankovic.domain.entities.airport;

import java.util.Arrays;

public enum DaylightSavingsTime {
    E("Europe"),
    A("US/Canada"),
    S("South America"),
    O("Australia"),
    Z("New Zeland"),
    N("None"),
    U("Unknown");

    private String daylightSavingsTime;

    DaylightSavingsTime(String daylightSavingsTime) {
        this.daylightSavingsTime = daylightSavingsTime;
    }

    public static DaylightSavingsTime fromString(String dstString) {
        return Arrays.asList(DaylightSavingsTime.values()).stream()
                .filter(dst -> dst.toString().equalsIgnoreCase(dstString))
                .findFirst()
                .orElse(U);
    }

    @Override
    public String toString() {
        return daylightSavingsTime;
    }
}
