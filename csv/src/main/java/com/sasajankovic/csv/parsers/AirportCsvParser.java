package com.sasajankovic.csv.parsers;

import com.sasajankovic.csv.entities.AirportCsv;
import com.sasajankovic.csv.repository.AirportCsvRepository;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.ports.out.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AirportCsvParser {
    private final AirportCsvRepository airportCsvRepository;
    private final CityRepository cityRepository;

    public List<Airport> parse() throws IOException {
        List<Airport> airports = new ArrayList<>();
        List<City> cities = cityRepository.fetchCities();
        for (AirportCsv airportCsv : airportCsvRepository.load()) {
            Optional<City> city = cities.stream().filter(airportCsv::isInTheCity).findFirst();
            if (city.isPresent()) {
                airports.add(airportCsv.toDomainEntity(city.get()));
            }
        }
        return airports;
    }
}
