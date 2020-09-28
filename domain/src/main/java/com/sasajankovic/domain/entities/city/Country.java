package com.sasajankovic.domain.entities.city;

import lombok.NonNull;

public class Country {
    private final String name;

    public Country(@NonNull String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Country name must not be empty");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
