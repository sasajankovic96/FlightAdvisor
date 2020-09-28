package com.sasajankovic.csv.parsers;

import com.sasajankovic.csv.entities.RouteCsv;
import com.sasajankovic.csv.repository.RouteCsvRepository;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.route.Route;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RouteCsvParser {
    private final RouteCsvRepository routeCsvRepository;

    public List<Route> parse(List<Airport> airports) throws IOException {
        List<Route> routes = new ArrayList<>();
        for (RouteCsv route : routeCsvRepository.load()) {
            Optional<Airport> sourceAirport = getSourceAirport(airports, route);
            Optional<Airport> destinationAirport = getDestinationAirport(airports, route);
            if (sourceAirport.isPresent() && destinationAirport.isPresent()) {
                routes.add(route.toDomainEntity(sourceAirport.get(), destinationAirport.get()));
            }
        }
        return routes;
    }

    private Optional<Airport> getSourceAirport(List<Airport> airports, RouteCsv route) {
        return airports.stream()
                .filter(
                        airport ->
                                airport.equalsByAirportId(route.getSourceAirportId())
                                        || airport.equalsByAirportCode(
                                                route.getSourceAirportCode()))
                .findFirst();
    }

    private Optional<Airport> getDestinationAirport(List<Airport> airports, RouteCsv route) {
        return airports.stream()
                .filter(
                        airport ->
                                airport.equalsByAirportId(route.getDestinationAirportId())
                                        || airport.equalsByAirportCode(
                                                route.getDesignationAirportCode()))
                .findFirst();
    }
}
