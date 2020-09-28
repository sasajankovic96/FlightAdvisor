package com.sasajankovic.domain.entities.city;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityId {
    private final Long id;

    public Long get() {
        return id;
    }

    @Override
    public int hashCode() {
        return 51 * id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CityId)) return false;
        CityId cityId = (CityId) obj;
        return cityId.get().equals(id);
    }
}
