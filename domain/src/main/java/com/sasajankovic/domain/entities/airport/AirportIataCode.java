package com.sasajankovic.domain.entities.airport;

import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import lombok.NonNull;

import java.util.Optional;

public class AirportIataCode {
    private final String code;

    private AirportIataCode(@NonNull String code) {
        if (code.trim().length() != FlightAdvisorConstants.AIRPORT_IATA_CODE_LENGTH)
            throw new IllegalArgumentException(
                    String.format(
                            "IATA code must be %d letter long",
                            FlightAdvisorConstants.AIRPORT_IATA_CODE_LENGTH));

        this.code = code;
    }

    public static AirportIataCode create(Optional<String> code) {
        return code.isPresent() ? new AirportIataCode(code.get()) : null;
    }

    @Override
    public String toString() {
        return code;
    }
}
