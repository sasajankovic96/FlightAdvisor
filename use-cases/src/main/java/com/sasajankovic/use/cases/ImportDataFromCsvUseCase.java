package com.sasajankovic.use.cases;

import com.sasajankovic.csv.parsers.AirportCsvParser;
import com.sasajankovic.csv.parsers.RouteCsvParser;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.route.Route;
import com.sasajankovic.domain.ports.in.ImportDataUseCase;
import com.sasajankovic.domain.ports.out.AirportRepository;
import com.sasajankovic.domain.ports.out.RouteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ImportDataFromCsvUseCase implements ImportDataUseCase {
    private final AirportCsvParser airportCsvParser;
    private final RouteCsvParser routeCsvParser;
    private final AirportRepository airportRepository;
    private final RouteRepository routeRepository;

    @Override
    public void importData() {
        try {
            List<Airport> airports = airportCsvParser.parse();
            List<Route> routes = routeCsvParser.parse(airports);

            airportRepository.saveAll(airports);
            routeRepository.saveAll(routes);
        } catch (IOException e) {
            log.error("Error while importing data. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
