package com.sasajankovic.domain.entities.airport;

import lombok.NonNull;

import java.util.Optional;

public class OlsonFormatTimezone {
    private final String timezone;

    private OlsonFormatTimezone(@NonNull String timezone) {
        this.timezone = timezone;
    }

    public static OlsonFormatTimezone create(Optional<String> timezone) {
        return timezone.isPresent() ? new OlsonFormatTimezone(timezone.get()) : null;
    }

    @Override
    public String toString() {
        return timezone;
    }
}
