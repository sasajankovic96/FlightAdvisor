package com.sasajankovic.domain.ports.out;

import com.sasajankovic.domain.entities.route.Route;

import java.util.List;

public interface RouteRepository {
    void saveAll(List<Route> routes);

    List<Route> findAll();
}
