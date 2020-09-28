package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.city.City;

import java.util.List;
import java.util.Optional;

public interface FetchCitiesUseCase {
    List<City> fetchCitiesWithComments(Optional<Integer> numberOfComments);
}
