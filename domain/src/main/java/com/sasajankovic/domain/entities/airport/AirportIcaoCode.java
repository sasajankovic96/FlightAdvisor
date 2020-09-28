package com.sasajankovic.domain.entities.airport;

import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import lombok.NonNull;

import java.util.Optional;

public class AirportIcaoCode {
    private final String code;

    private AirportIcaoCode(@NonNull String code) {
        if (code.trim().length() != FlightAdvisorConstants.AIRPORT_ICAO_CODE_LENGTH)
            throw new IllegalArgumentException(
                    String.format(
                            "ICAO code must be %d characters long",
                            FlightAdvisorConstants.AIRPORT_ICAO_CODE_LENGTH));
        this.code = code;
    }

    public static AirportIcaoCode create(Optional<String> code) {
        return code.isPresent() ? new AirportIcaoCode(code.get()) : null;
    }

    @Override
    public String toString() {
        return code;
    }
}
