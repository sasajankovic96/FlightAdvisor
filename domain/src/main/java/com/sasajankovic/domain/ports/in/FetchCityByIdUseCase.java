package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;

public interface FetchCityByIdUseCase {
    City fetchById(CityId id) throws EntityNotFoundException;
}
