package com.sasajankovic.domain.entities.user;

import lombok.NonNull;

public class LastName {
    private final String lastName;

    public LastName(@NonNull String lastName) {
        if (lastName.isBlank()) throw new IllegalArgumentException("Last name must not be empty");

        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return lastName;
    }
}
