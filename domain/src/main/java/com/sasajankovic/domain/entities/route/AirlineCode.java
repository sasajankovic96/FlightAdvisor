package com.sasajankovic.domain.entities.route;

import lombok.NonNull;

import java.util.Optional;

public class AirlineCode {
    private static final int AIRLINE_IATA_CODE_LENGTH = 2;
    private static final int AIRLINE_ICAO_CODE_LENGTH = 3;

    private final String code; // 2 letter IATA or 3 letter IACA code

    private AirlineCode(@NonNull String code) {
        if (code.length() != AIRLINE_IATA_CODE_LENGTH && code.length() != AIRLINE_ICAO_CODE_LENGTH)
            throw new IllegalArgumentException(
                    String.format(
                            "Airline code must be %d or %d characters long",
                            AIRLINE_IATA_CODE_LENGTH, AIRLINE_ICAO_CODE_LENGTH));

        this.code = code;
    }

    public static AirlineCode create(Optional<String> code) {
        return code.isPresent() ? new AirlineCode(code.get()) : null;
    }

    public String get() {
        return code;
    }
}
