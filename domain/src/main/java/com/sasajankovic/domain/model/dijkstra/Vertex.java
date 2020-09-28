package com.sasajankovic.domain.model.dijkstra;

import com.sasajankovic.domain.entities.airport.Airport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Vertex {
    private final Airport airport;

    @Override
    public int hashCode() {
        return 31 * airport.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Vertex)) return false;
        Vertex vertex = (Vertex) obj;
        return vertex.getAirport().equals(airport);
    }

    @Override
    public String toString() {
        return airport.getName().toString();
    }
}
