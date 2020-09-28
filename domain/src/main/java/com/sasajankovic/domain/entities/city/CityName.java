package com.sasajankovic.domain.entities.city;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CityName {
    private final String name;

    public CityName(@NonNull String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("City name must not be empty");
        }

        this.name = name;
    }

    @Override
    public int hashCode() {
        return 51 + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CityName)) return false;
        return ((CityName) obj).getName().equals(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
