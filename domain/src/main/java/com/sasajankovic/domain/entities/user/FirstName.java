package com.sasajankovic.domain.entities.user;

import lombok.NonNull;

public class FirstName {
    private final String firstName;

    public FirstName(@NonNull String firstName) {
        if (firstName.isBlank()) throw new IllegalArgumentException("First name must not be empty");

        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return firstName;
    }
}
