package com.sasajankovic.domain.ports.out;

import com.sasajankovic.domain.entities.airport.Airport;

import java.util.List;

public interface AirportRepository {
    void saveAll(List<Airport> airports);
}
