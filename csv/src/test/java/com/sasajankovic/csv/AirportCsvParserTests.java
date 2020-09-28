package com.sasajankovic.csv;

import com.sasajankovic.csv.entities.AirportCsv;
import com.sasajankovic.csv.parsers.AirportCsvParser;
import com.sasajankovic.csv.repository.AirportCsvRepository;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.ports.out.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AirportCsvParserTests {
    @Mock private AirportCsvRepository airportCsvRepository;

    @Mock private CityRepository cityRepository;

    @InjectMocks private AirportCsvParser airportCsvParser;

    private List<AirportCsv> airportCsvList;
    private List<City> cities;

    @BeforeEach
    public void setup() {
        cities =
                Arrays.asList(
                        createCity(1l, "London", "UK", "The capitol of England"),
                        createCity(2l, "Berlin", "Germany", "The capitol of Germany"),
                        createCity(3l, "Belgrade", "Serbia", "The capitol of Serbia"));
        airportCsvList =
                Arrays.asList(
                        createAirport(1l, "Nikola Tesla", "Belgrade", "Serbia", "BEG"),
                        createAirport(2l, "Charles De Gaulle", "Paris", "France", "CDG"),
                        createAirport(
                                3l, "McCarran International Airport", "Las Vegas", "USA", "LAS"),
                        createAirport(4l, "Heathrow", "London", "UK", "UKC"),
                        createAirport(
                                5l,
                                "Moscow Sheremetyevo International Airport",
                                "Moscow",
                                "Russia",
                                "RUS"));
    }

    @Test
    public void ShouldParseCsvContent() throws IOException {
        Mockito.when(cityRepository.fetchCities()).thenReturn(cities);
        Mockito.when(airportCsvRepository.load()).thenReturn(airportCsvList);

        List<Airport> result = airportCsvParser.parse();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Nikola Tesla", result.get(0).getName().toString());
        Assertions.assertEquals("Heathrow", result.get(1).getName().toString());
    }

    private AirportCsv createAirport(
            Long id, String name, String city, String country, String iataCode) {
        return new AirportCsv(
                id, name, city, country, iataCode, null, null, null, null, null, null, null, null,
                null);
    }

    private City createCity(Long id, String name, String country, String description) {
        return new City(
                new CityId(id),
                new CityName(name),
                new CityDescription(description),
                new Country(country),
                Collections.emptyList());
    }
}
