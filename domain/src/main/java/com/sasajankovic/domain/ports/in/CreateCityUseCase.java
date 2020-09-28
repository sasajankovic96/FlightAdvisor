package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;

public interface CreateCityUseCase {
    CityId createCity(City city);
}
