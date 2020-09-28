package com.sasajankovic.persistence.repositories;

import com.sasajankovic.domain.entities.route.Route;
import com.sasajankovic.domain.ports.out.RouteRepository;
import com.sasajankovic.persistence.jpa.RouteJpaRepository;
import com.sasajankovic.persistence.mappers.RoutePersistenceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {
    private final RouteJpaRepository routeJpaRepository;
    private final RoutePersistenceMapper routePersistenceMapper;

    @Override
    public void saveAll(List<Route> routes) {
        routeJpaRepository.saveAll(
                routes.stream()
                        .map(routePersistenceMapper::toPersistentEntity)
                        .collect(Collectors.toList()));
    }

    @Override
    public List<Route> findAll() {
        return routeJpaRepository.findAll().stream()
                .map(routePersistenceMapper::toDomainEntity)
                .collect(Collectors.toList());
    }
}
