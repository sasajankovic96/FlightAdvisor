package com.sasajankovic.domain.entities.city;

import lombok.NonNull;

public class CityDescription {
    private final String description;

    public CityDescription(@NonNull String description) {
        if (description.isEmpty())
            throw new IllegalArgumentException("City description must not be empty");

        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
