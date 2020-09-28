package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.ports.in.FetchCityByIdUseCase;
import com.sasajankovic.domain.ports.out.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FetchCityByIdUseCaseImpl implements FetchCityByIdUseCase {
    private final CityRepository cityRepository;

    @Override
    public City fetchById(CityId id) throws EntityNotFoundException {
        return cityRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        String.format("City with id %d not found", id.get())));
    }
}
