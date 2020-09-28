package com.sasajankovic.domain.entities.airport;

import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import lombok.NonNull;

import java.util.Optional;

public class Longitude {
    private final Double longitude;

    public Longitude(@NonNull Double longitude) {
        if (longitude < FlightAdvisorConstants.MIN_LONGITUDE
                || longitude > FlightAdvisorConstants.MAX_LONGITUDE)
            throw new IllegalArgumentException(
                    String.format(
                            "Longitude must be be between %f and %f",
                            FlightAdvisorConstants.MIN_LONGITUDE,
                            FlightAdvisorConstants.MAX_LONGITUDE));

        this.longitude = longitude;
    }

    public static Longitude of(Optional<Double> longitude) {
        return longitude.isPresent() ? new Longitude(longitude.get()) : null;
    }

    public Double get() {
        return longitude;
    }
}
