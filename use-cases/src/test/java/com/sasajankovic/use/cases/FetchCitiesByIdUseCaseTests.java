package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.ports.out.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FetchCitiesByIdUseCaseTests {
    @Mock private CityRepository cityRepository;

    @InjectMocks private FetchCityByIdUseCaseImpl fetchCityByIdUseCase;

    private City city;
    private CityId cityId;

    @BeforeEach
    public void setup() {
        cityId = new CityId(1l);
        city =
                new City(
                        cityId,
                        new CityName("Novi Sad"),
                        new CityDescription("An awesome city"),
                        new Country("Serbia"),
                        Collections.emptyList());
    }

    @Test
    public void ShouldReturnNotFoundWhenCityDoesNotExist() {
        Mockito.when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class, () -> fetchCityByIdUseCase.fetchById(cityId));
    }

    @Test
    public void ShouldReturnCity() {
        Mockito.when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        City result = fetchCityByIdUseCase.fetchById(cityId);

        Assertions.assertEquals(cityId, result.getId());
    }
}
