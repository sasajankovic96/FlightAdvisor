package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.Money;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.airport.AirportIataCode;
import com.sasajankovic.domain.entities.airport.AirportName;
import com.sasajankovic.domain.entities.airport.DaylightSavingsTime;
import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.route.Route;
import com.sasajankovic.domain.ports.out.RouteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sasajankovic.use.cases.utils.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class FindShortestRouteUseCaseTests {
    @Mock private RouteRepository routeRepository;
    @InjectMocks private FindShortestRouteUseCaseImpl findShortestRouteUseCase;

    private List<Route> routes;
    private List<City> cities;

    @Test
    public void FindShortestRouteWithOneSourceAndDestinationAirport() {
        Mockito.when(routeRepository.findAll()).thenReturn(routes);

        List<Route> result =
                findShortestRouteUseCase.findShortestRoute(new CityId(1l), new CityId(5l));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1l, result.get(0).getId());
        Assertions.assertEquals(2l, result.get(1).getId());
    }

    @Test
    public void FindShortestRouteWhenTheRouteDoesNotExist() {
        Mockito.when(routeRepository.findAll()).thenReturn(routes);

        List<Route> result =
                findShortestRouteUseCase.findShortestRoute(new CityId(1l), new CityId(6l));

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void FindShortestRouteWithOneSourceAndMultiDestinationAirports() {
        Mockito.when(routeRepository.findAll()).thenReturn(routes);

        List<Route> result =
                findShortestRouteUseCase.findShortestRoute(new CityId(1l), new CityId(3l));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(4, result.get(0).getId());
        Assertions.assertEquals(6, result.get(1).getId());
    }

    @Test
    public void FindShortestRouteWithMultiSourceAndMultiDestinationAirports() {
        Mockito.when(routeRepository.findAll()).thenReturn(routes);

        List<Route> result =
                findShortestRouteUseCase.findShortestRoute(new CityId(4l), new CityId(3l));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(6, result.get(0).getId());
    }

    @BeforeEach
    public void setup() {
        cities =
                Arrays.asList(
                        createCity(1l, "Belgrade", "Capital city of Serbia", "Serbia"),
                        createCity(2l, "Madrid", "Capitol city of Spain", "Spain"),
                        createCity(3l, "Vegas", "The city of sin", "USA"),
                        createCity(4l, "Los Angeles", "The city of angeles", "USA"),
                        createCity(
                                5l, "Prague", "Capitol city of Czech republic", "Czech Republic"),
                        createCity(6l, "Paris", "The city of love and lights", "France"));
        Map<String, Airport> airportsMap =
                Arrays.asList(
                                createAirport(1l, NIKOLA_TESLA, cities.get(0), "BEG"),
                                createAirport(2l, BARAJAS_ADOLFO_SUÁREZ, cities.get(1), "MAD"),
                                createAirport(
                                        3l, MCCARRAN_INTERNATIONAL_AIRPORT, cities.get(2), "LAS"),
                                createAirport(4l, VEGAS_AIRPORT_2, cities.get(2), "VAS"),
                                createAirport(
                                        5l,
                                        LOS_ANGELES_INTERNATIONAL_AIRPORT,
                                        cities.get(3),
                                        "LAX"),
                                createAirport(6l, LOS_ANGELES_AIRPORT_2, cities.get(3), "LAL"),
                                createAirport(7l, PRAGUE_AIRPORT, cities.get(4), "PRG"),
                                createAirport(8l, CHARLES_DE_GAULLE, cities.get(5), "CDG"))
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        airport -> airport.getName().toString(),
                                        Function.identity()));
        routes =
                Arrays.asList(
                        createRoute(
                                1l,
                                airportsMap.get(NIKOLA_TESLA),
                                airportsMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                BigDecimal.valueOf(100)),
                        createRoute(
                                2l,
                                airportsMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                airportsMap.get(PRAGUE_AIRPORT),
                                BigDecimal.valueOf(150)),
                        createRoute(
                                3l,
                                airportsMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                airportsMap.get(MCCARRAN_INTERNATIONAL_AIRPORT),
                                BigDecimal.valueOf(300)),
                        createRoute(
                                4l,
                                airportsMap.get(NIKOLA_TESLA),
                                airportsMap.get(LOS_ANGELES_INTERNATIONAL_AIRPORT),
                                BigDecimal.valueOf(70)),
                        createRoute(
                                5l,
                                airportsMap.get(LOS_ANGELES_INTERNATIONAL_AIRPORT),
                                airportsMap.get(MCCARRAN_INTERNATIONAL_AIRPORT),
                                BigDecimal.valueOf(120)),
                        createRoute(
                                6l,
                                airportsMap.get(LOS_ANGELES_INTERNATIONAL_AIRPORT),
                                airportsMap.get(VEGAS_AIRPORT_2),
                                BigDecimal.valueOf(80)),
                        createRoute(
                                7l,
                                airportsMap.get(CHARLES_DE_GAULLE),
                                airportsMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                BigDecimal.valueOf(500)),
                        createRoute(
                                8l,
                                airportsMap.get(PRAGUE_AIRPORT),
                                airportsMap.get(LOS_ANGELES_INTERNATIONAL_AIRPORT),
                                BigDecimal.valueOf(350)),
                        createRoute(
                                9l,
                                airportsMap.get(LOS_ANGELES_AIRPORT_2),
                                airportsMap.get(MCCARRAN_INTERNATIONAL_AIRPORT),
                                BigDecimal.valueOf(100)));
    }

    private City createCity(Long id, String name, String description, String country) {
        return new City(
                new CityId(id),
                new CityName(name),
                new CityDescription(description),
                new Country(country),
                Collections.emptyList());
    }

    private Airport createAirport(Long id, String name, City city, String code) {
        return new Airport(
                id,
                AirportName.create(name),
                city,
                AirportIataCode.create(Optional.of(code)),
                null,
                null,
                null,
                null,
                null,
                DaylightSavingsTime.E,
                null,
                null,
                null);
    }

    private Route createRoute(
            Long id, Airport sourceAirport, Airport destinationAirport, BigDecimal cost) {
        return new Route(
                id,
                null,
                null,
                sourceAirport,
                destinationAirport,
                true,
                0,
                Collections.emptyList(),
                Money.create(cost));
    }
}
