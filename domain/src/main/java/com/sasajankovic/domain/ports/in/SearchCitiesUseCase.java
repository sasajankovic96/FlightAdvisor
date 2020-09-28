package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.model.SearchCityParams;

import java.util.List;

public interface SearchCitiesUseCase {
    List<City> searchCities(SearchCityParams searchParams);
}
