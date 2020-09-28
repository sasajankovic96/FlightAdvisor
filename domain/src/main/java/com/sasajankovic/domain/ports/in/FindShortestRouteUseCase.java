package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.entities.route.Route;

import java.util.List;

public interface FindShortestRouteUseCase {
    List<Route> findShortestRoute(CityId sourceCityId, CityId destinationCityId);
}
