package com.sasajankovic.persistence.repositories;

import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.ports.out.AirportRepository;
import com.sasajankovic.persistence.jpa.AirportJpaRepository;
import com.sasajankovic.persistence.mappers.AirportPersistenceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirportRepositoryImpl implements AirportRepository {
    private final AirportJpaRepository airportJpaRepository;
    private final AirportPersistenceMapper airportPersistenceMapper;

    public void saveAll(List<Airport> airports) {
        airportJpaRepository.saveAll(
                airports.stream()
                        .map(airportPersistenceMapper::toPersistentEntity)
                        .collect(Collectors.toList()));
    }
}
