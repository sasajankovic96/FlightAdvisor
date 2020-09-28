package com.sasajankovic.domain.entities.route;

import lombok.NonNull;

import java.util.Optional;

public class AirlineId {
    private final Long id;

    private AirlineId(@NonNull Long id) {
        this.id = id;
    }

    public static AirlineId create(Optional<Long> id) {
        return id.isPresent() ? new AirlineId(id.get()) : null;
    }

    public Long get() {
        return id;
    }

    @Override
    public int hashCode() {
        return 51 * id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AirlineId)) return false;
        return ((AirlineId) obj).get().equals(id);
    }
}
