package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.ports.in.FetchCitiesUseCase;
import com.sasajankovic.domain.ports.out.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FetchCitiesUseCaseImpl implements FetchCitiesUseCase {
    private final CityRepository cityRepository;

    @Override
    public List<City> fetchCitiesWithComments(Optional<Integer> numberOfComments) {
        List<City> cities = cityRepository.fetchCities();
        return numberOfComments.isPresent()
                ? cities.stream()
                        .map(city -> city.withSortedLatestComments(numberOfComments.get()))
                        .collect(Collectors.toList())
                : cities.stream()
                        .map(city -> city.withSortedComments())
                        .collect(Collectors.toList());
    }
}
