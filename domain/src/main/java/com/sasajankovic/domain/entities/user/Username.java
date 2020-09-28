package com.sasajankovic.domain.entities.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Username {
    private static final int MIN_LENGTH = 5;

    private final String username;

    private Username(@NonNull String username) {
        if (username.isBlank()) throw new IllegalArgumentException("Username must not be empty");
        if (username.length() < MIN_LENGTH)
            throw new IllegalArgumentException(
                    String.format("Username must be at least %d characters long", MIN_LENGTH));

        this.username = username;
    }

    public static Username of(@NonNull String username) {
        return new Username(username);
    }

    @Override
    public int hashCode() {
        return 41 * username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Username)) return false;
        return ((Username) obj).getUsername().equals(username);
    }

    @Override
    public String toString() {
        return username;
    }
}
