package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityName;
import com.sasajankovic.domain.model.SearchCityParams;
import com.sasajankovic.domain.ports.in.SearchCitiesUseCase;
import com.sasajankovic.domain.ports.out.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchCitiesUseCaseImpl implements SearchCitiesUseCase {
    private final CityRepository cityRepository;

    @Override
    public List<City> searchCities(SearchCityParams searchParams) {
        List<City> cities = cityRepository.searchByName(new CityName(searchParams.getName()));
        Optional<Integer> numberOfComments = searchParams.getNumberOfComments();
        return numberOfComments.isPresent()
                ? cities.stream()
                        .map(city -> city.withSortedLatestComments(numberOfComments.get()))
                        .collect(Collectors.toList())
                : cities.stream()
                        .map(city -> city.withSortedComments())
                        .collect(Collectors.toList());
    }
}
