package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.Money;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.entities.route.Route;
import com.sasajankovic.domain.model.dijkstra.Edge;
import com.sasajankovic.domain.model.dijkstra.Graph;
import com.sasajankovic.domain.model.dijkstra.Vertex;
import com.sasajankovic.domain.ports.in.FindShortestRouteUseCase;
import com.sasajankovic.domain.ports.out.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindShortestRouteUseCaseImpl implements FindShortestRouteUseCase {

    private final RouteRepository routeRepository;

    @Override
    public List<Route> findShortestRoute(CityId sourceCityId, CityId destinationCityId) {
        List<Route> routes = routeRepository.findAll();

        List<Airport> sourceAirports = getSourceAirports(routes, sourceCityId);
        List<Airport> destinationAirports = getDestinationAirports(routes, destinationCityId);

        if (sourceAirports.isEmpty() || destinationAirports.isEmpty())
            return Collections.emptyList();

        Map<Airport, Vertex> vertices = getVertices(routes);
        List<Vertex> vertexList = vertices.values().stream().collect(Collectors.toList());

        List<Edge> edges = getEdges(routes, vertexList);

        Graph graph = new Graph(vertexList, edges);

        List<Route> shortestRoute = new ArrayList<>();
        for (Airport sourceAirport : sourceAirports) {
            for (Airport destinationAirport : destinationAirports) {
                List<Route> route =
                        graph
                                .findShortestPath(
                                        vertices.get(sourceAirport),
                                        vertices.get(destinationAirport))
                                .stream()
                                .map(Edge::getRoute)
                                .collect(Collectors.toList());
                if (!route.isEmpty()
                        && getRouteCost(route).compareTo(getRouteCost(shortestRoute)) < 0) {
                    shortestRoute = route;
                }
            }
        }

        return shortestRoute;
    }

    private List<Airport> getSourceAirports(List<Route> routes, CityId cityId) {
        return routes.stream()
                .filter(route -> route.getSourceCity().getId().equals(cityId))
                .map(Route::getSourceAirport)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Airport> getDestinationAirports(List<Route> routes, CityId cityId) {
        return routes.stream()
                .filter(route -> route.getDestinationCity().getId().equals(cityId))
                .map(Route::getDestinationAirport)
                .distinct()
                .collect(Collectors.toList());
    }

    private Map<Airport, Vertex> getVertices(List<Route> routes) {
        return routes.stream()
                .map(
                        route ->
                                Arrays.asList(
                                        route.getSourceAirport(), route.getDestinationAirport()))
                .flatMap(List::stream)
                .map(Vertex::new)
                .distinct()
                .collect(Collectors.toMap(Vertex::getAirport, Function.identity()));
    }

    private List<Edge> getEdges(List<Route> routes, List<Vertex> vertices) {
        Map<String, Edge> edges = new HashMap<>();

        for (Route route : routes) {
            Vertex sourceVertex =
                    vertices.stream()
                            .filter(vertex -> vertex.getAirport().equals(route.getSourceAirport()))
                            .findFirst()
                            .get();
            Vertex destinationVertex =
                    vertices.stream()
                            .filter(
                                    vertex ->
                                            vertex.getAirport()
                                                    .equals(route.getDestinationAirport()))
                            .findFirst()
                            .get();
            Edge edge = new Edge(route, sourceVertex, destinationVertex);
            edges.compute(
                    route.getRoutePathIdentifier(),
                    (key, value) ->
                            Objects.isNull(value)
                                    ? edge
                                    : value.compareTo(edge) > 0 ? edge : value);
        }

        return edges.values().stream().collect(Collectors.toList());
    }

    private Money getRouteCost(List<Route> route) {
        return route.isEmpty()
                ? Money.create(BigDecimal.valueOf(Integer.MAX_VALUE))
                : route.stream().map(Route::getPrice).reduce(Money.ZERO, (a, b) -> a.add(b));
    }
}
