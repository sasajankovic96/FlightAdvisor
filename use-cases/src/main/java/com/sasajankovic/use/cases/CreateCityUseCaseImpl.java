package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.ports.in.CreateCityUseCase;
import com.sasajankovic.domain.ports.out.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCityUseCaseImpl implements CreateCityUseCase {

    private final CityRepository cityRepository;

    @Override
    public CityId createCity(City city) {
        return cityRepository.create(city);
    }
}
