package com.sasajankovic.domain;

import com.sasajankovic.domain.entities.Money;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.airport.AirportIataCode;
import com.sasajankovic.domain.entities.airport.AirportName;
import com.sasajankovic.domain.entities.airport.DaylightSavingsTime;
import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.route.Route;
import com.sasajankovic.domain.model.dijkstra.Edge;
import com.sasajankovic.domain.model.dijkstra.Graph;
import com.sasajankovic.domain.model.dijkstra.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sasajankovic.domain.TestConstants.*;

public class GraphTest {

    private Map<String, Vertex> vertices;
    private List<Edge> edges;

    @Test
    public void ShouldFindShortestRouteWhenTheRouteExists() {
        Graph graph = new Graph(vertices.values().stream().collect(Collectors.toList()), edges);

        List<Edge> route =
                graph.findShortestPath(
                        vertices.get(NIKOLA_TESLA),
                        vertices.get(LOS_ANGELES_INTERNATIONAL_AIRPORT));

        Assertions.assertEquals(3, route.size());
        Assertions.assertEquals(1l, route.get(0).getRoute().getId());
        Assertions.assertEquals(3l, route.get(1).getRoute().getId());
        Assertions.assertEquals(5l, route.get(2).getRoute().getId());
    }

    @Test
    public void ShouldNotFindAnyRouteWhenTheRouteDoesNotExist() {
        Graph graph = new Graph(vertices.values().stream().collect(Collectors.toList()), edges);

        List<Edge> route =
                graph.findShortestPath(vertices.get(NIKOLA_TESLA), vertices.get(CHARLES_DE_GAULLE));

        Assertions.assertEquals(0, route.size());
    }

    @BeforeEach
    private void setup() {
        List<City> cities =
                Arrays.asList(
                        createCity(1l, "Belgrade", "Capital city of Serbia", "Serbia"),
                        createCity(2l, "Madrid", "Capitol city of Spain", "Spain"),
                        createCity(3l, "Vegas", "The city of sin", "USA"),
                        createCity(4l, "Los Angeles", "The city of angeles", "USA"),
                        createCity(
                                5l, "Prague", "Capitol city of Czech republic", "Czech republic"),
                        createCity(6l, "Paris", "The city of love and lights", "France"));
        List<Airport> airports =
                Arrays.asList(
                        createAirport(1l, NIKOLA_TESLA, cities.get(0), "BEG"),
                        createAirport(2l, BARAJAS_ADOLFO_SUÁREZ, cities.get(1), "MAD"),
                        createAirport(3l, MCCARRAN_INTERNATIONAL_AIRPORT, cities.get(2), "LAS"),
                        createAirport(4l, LOS_ANGELES_INTERNATIONAL_AIRPORT, cities.get(3), "LAX"),
                        createAirport(5l, PRAGUE_AIRPORT, cities.get(4), "PRG"),
                        createAirport(6l, CHARLES_DE_GAULLE, cities.get(5), "CDG"));
        Map<String, Airport> airportMap =
                airports.stream()
                        .collect(
                                Collectors.toMap(
                                        airport -> airport.getName().toString(),
                                        Function.identity()));

        vertices =
                airports.stream()
                        .collect(
                                Collectors.toMap(
                                        airport -> airport.getName().toString(), Vertex::new));

        edges =
                Arrays.asList(
                        new Edge(
                                createRoute(
                                        1l,
                                        airportMap.get(NIKOLA_TESLA),
                                        airportMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                        Money.create(BigDecimal.valueOf(30))),
                                vertices.get(NIKOLA_TESLA),
                                vertices.get(BARAJAS_ADOLFO_SUÁREZ)),
                        new Edge(
                                createRoute(
                                        2l,
                                        airportMap.get(NIKOLA_TESLA),
                                        airportMap.get(MCCARRAN_INTERNATIONAL_AIRPORT),
                                        Money.create(BigDecimal.valueOf(500))),
                                vertices.get(NIKOLA_TESLA),
                                vertices.get(MCCARRAN_INTERNATIONAL_AIRPORT)),
                        new Edge(
                                createRoute(
                                        3l,
                                        airportMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                        airportMap.get(PRAGUE_AIRPORT),
                                        Money.create(BigDecimal.valueOf(50))),
                                vertices.get(BARAJAS_ADOLFO_SUÁREZ),
                                vertices.get(PRAGUE_AIRPORT)),
                        new Edge(
                                createRoute(
                                        4l,
                                        airportMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                        airportMap.get(LOS_ANGELES_INTERNATIONAL_AIRPORT),
                                        Money.create(BigDecimal.valueOf(300))),
                                vertices.get(BARAJAS_ADOLFO_SUÁREZ),
                                vertices.get(LOS_ANGELES_INTERNATIONAL_AIRPORT)),
                        new Edge(
                                createRoute(
                                        5l,
                                        airportMap.get(PRAGUE_AIRPORT),
                                        airportMap.get(LOS_ANGELES_INTERNATIONAL_AIRPORT),
                                        Money.create(BigDecimal.valueOf(80))),
                                vertices.get(PRAGUE_AIRPORT),
                                vertices.get(LOS_ANGELES_INTERNATIONAL_AIRPORT)),
                        new Edge(
                                createRoute(
                                        6l,
                                        airportMap.get(CHARLES_DE_GAULLE),
                                        airportMap.get(BARAJAS_ADOLFO_SUÁREZ),
                                        Money.create(BigDecimal.valueOf(300))),
                                vertices.get(CHARLES_DE_GAULLE),
                                vertices.get(BARAJAS_ADOLFO_SUÁREZ)),
                        new Edge(
                                createRoute(
                                        7l,
                                        airportMap.get(MCCARRAN_INTERNATIONAL_AIRPORT),
                                        airportMap.get(LOS_ANGELES_INTERNATIONAL_AIRPORT),
                                        Money.create(BigDecimal.valueOf(100))),
                                vertices.get(MCCARRAN_INTERNATIONAL_AIRPORT),
                                vertices.get(LOS_ANGELES_INTERNATIONAL_AIRPORT)));
    }

    private Route createRoute(
            Long id, Airport sourceAirport, Airport destinationAirport, Money price) {
        return new Route(
                id,
                null,
                null,
                sourceAirport,
                destinationAirport,
                true,
                0,
                Collections.emptyList(),
                price);
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
}
