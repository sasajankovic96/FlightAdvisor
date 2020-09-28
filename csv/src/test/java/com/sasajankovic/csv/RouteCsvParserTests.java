package com.sasajankovic.csv;

import com.sasajankovic.csv.entities.RouteCsv;
import com.sasajankovic.csv.parsers.RouteCsvParser;
import com.sasajankovic.csv.repository.RouteCsvRepository;
import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import com.sasajankovic.domain.entities.airport.*;
import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.route.AirlineId;
import com.sasajankovic.domain.entities.route.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RouteCsvParserTests {

    @Mock private RouteCsvRepository routeCsvRepository;

    @InjectMocks private RouteCsvParser routeCsvParser;

    private List<RouteCsv> routes;
    private List<Airport> airports;

    @Test
    public void ShouldParseCsvContent() throws IOException {
        Mockito.when(routeCsvRepository.load()).thenReturn(routes);

        List<Route> routes = routeCsvParser.parse(airports);

        Assertions.assertEquals(2, routes.size());
        Assertions.assertEquals(
                AirlineId.create(Optional.of(1l)), routes.get(0).getAirlineId().get());
        Assertions.assertEquals(
                AirlineId.create(Optional.of(3l)), routes.get(1).getAirlineId().get());
    }

    @BeforeEach
    public void setup() {
        List<City> cities =
                Arrays.asList(
                        createCity(1l, "Belgrade", "The capitol of Serbia", "Serbia"),
                        createCity(2l, "Berlin", "The capitol of Germany", "Germany"),
                        createCity(3l, "Paris", "The capitol of France", "France"),
                        createCity(4l, "Moscow", "The capitol of Russia", "Russia"));

        airports =
                Arrays.asList(
                        createAirport(
                                1l,
                                "Nikola Tesla",
                                cities.get(0),
                                Optional.of("BEL"),
                                Optional.empty()),
                        createAirport(
                                2l,
                                "Brandenburg",
                                cities.get(1),
                                Optional.empty(),
                                Optional.of("CBEL")),
                        createAirport(
                                3l,
                                "Charles De Gaulle",
                                cities.get(2),
                                Optional.of("CDG"),
                                Optional.of("FCDG")),
                        createAirport(
                                4l,
                                "Sheremetyevo",
                                cities.get(3),
                                Optional.of("SVO"),
                                Optional.of("RSVO")));
        routes =
                Arrays.asList(
                        createRoute(
                                1l,
                                Optional.of("BEL"),
                                Optional.of("CBEL"),
                                Optional.empty(),
                                Optional.empty(),
                                BigDecimal.valueOf(100)),
                        createRoute(
                                2l,
                                Optional.of("SVO"),
                                Optional.of("LAL"),
                                Optional.of(4l),
                                Optional.of(17l),
                                BigDecimal.valueOf(200)),
                        createRoute(
                                3l,
                                Optional.empty(),
                                Optional.of("FCDG"),
                                Optional.of(2l),
                                Optional.empty(),
                                BigDecimal.valueOf(150)),
                        createRoute(
                                4l,
                                Optional.empty(),
                                Optional.of("RSVO"),
                                Optional.of(17l),
                                Optional.empty(),
                                BigDecimal.valueOf(230)));
    }

    private RouteCsv createRoute(
            Long airlineId,
            Optional<String> sourceAirportCode,
            Optional<String> destinationAirportCode,
            Optional<Long> sourceAirportId,
            Optional<Long> destinationAirportId,
            BigDecimal price) {
        return new RouteCsv(
                FlightAdvisorConstants.NO_VALUE,
                airlineId.toString(),
                sourceAirportCode.orElse(FlightAdvisorConstants.NO_VALUE),
                sourceAirportId.map(id -> id.toString()).orElse(FlightAdvisorConstants.NO_VALUE),
                destinationAirportCode.orElse(FlightAdvisorConstants.NO_VALUE),
                destinationAirportId
                        .map(id -> id.toString())
                        .orElse(FlightAdvisorConstants.NO_VALUE),
                FlightAdvisorConstants.NO_VALUE,
                "0",
                FlightAdvisorConstants.NO_VALUE,
                price);
    }

    private Airport createAirport(
            Long id, String name, City city, Optional<String> iataCode, Optional<String> icaoCode) {
        return new Airport(
                id,
                AirportName.create(name),
                city,
                AirportIataCode.create(iataCode),
                AirportIcaoCode.create(icaoCode),
                null,
                null,
                null,
                null,
                DaylightSavingsTime.E,
                null,
                null,
                null);
    }

    private City createCity(Long id, String name, String description, String country) {
        return new City(
                new CityId(id),
                new CityName(name),
                new CityDescription(description),
                new Country(country),
                Collections.emptyList());
    }
}
