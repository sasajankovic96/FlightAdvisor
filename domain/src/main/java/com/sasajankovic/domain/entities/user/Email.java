package com.sasajankovic.domain.entities.user;

import lombok.NonNull;

public class Email {
    private final String email;

    public Email(@NonNull String email) {
        if (email.isBlank()) throw new IllegalArgumentException("Email must not be empty");

        this.email = email;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Email)) return false;
        return obj.toString().equals(email);
    }

    @Override
    public String toString() {
        return email;
    }
}
