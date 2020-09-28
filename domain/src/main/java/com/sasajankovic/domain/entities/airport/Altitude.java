package com.sasajankovic.domain.entities.airport;

import lombok.NonNull;

import java.util.Optional;

public class Altitude {
    private final Double altitude;

    private Altitude(@NonNull Double altitude) {
        this.altitude = altitude;
    }

    public static Altitude of(Optional<Double> altitude) {
        return altitude.isPresent() ? new Altitude(altitude.get()) : null;
    }

    public Double get() {
        return altitude;
    }
}
