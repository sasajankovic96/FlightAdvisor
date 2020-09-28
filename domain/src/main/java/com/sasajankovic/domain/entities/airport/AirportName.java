package com.sasajankovic.domain.entities.airport;

import lombok.NonNull;

public class AirportName {
    private final String name;

    private AirportName(@NonNull String name) {
        if (name.isBlank()) throw new IllegalArgumentException("Airport name can't be null");

        this.name = name;
    }

    public static AirportName create(@NonNull String name) {
        return new AirportName(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
