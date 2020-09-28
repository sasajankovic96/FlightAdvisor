package com.sasajankovic.domain.entities.airport;

import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import lombok.NonNull;

import java.util.Optional;

public class Latitude {
    private final Double latitude;

    private Latitude(@NonNull Double latitude) {
        if (latitude < FlightAdvisorConstants.MIN_LATITUDE
                || latitude > FlightAdvisorConstants.MAX_LATITUDE) {
            throw new IllegalArgumentException(
                    String.format(
                            "Latitude must be between %f and %f",
                            FlightAdvisorConstants.MIN_LATITUDE,
                            FlightAdvisorConstants.MAX_LATITUDE));
        }
        this.latitude = latitude;
    }

    public static Latitude of(Optional<Double> latitude) {
        return latitude.isPresent() ? new Latitude(latitude.get()) : null;
    }

    public Double get() {
        return latitude;
    }
}
