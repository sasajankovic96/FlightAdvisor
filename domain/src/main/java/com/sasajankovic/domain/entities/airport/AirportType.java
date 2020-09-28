package com.sasajankovic.domain.entities.airport;

import lombok.NonNull;

import java.util.Optional;

public class AirportType {
    private final String type;

    private AirportType(@NonNull String type) {
        this.type = type;
    }

    public static AirportType create(Optional<String> type) {
        return type.isPresent() ? new AirportType(type.get()) : null;
    }

    @Override
    public String toString() {
        return type;
    }
}
