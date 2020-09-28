package com.sasajankovic.domain.entities.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Password {
    private final String password;

    public String get() {
        return password;
    }

    @Override
    public int hashCode() {
        return 51 * password.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Password)) return false;
        return ((Password) obj).get().equals(password);
    }

    @Override
    public String toString() {
        return password;
    }
}
