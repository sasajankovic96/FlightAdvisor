package com.sasajankovic.persistence.mappers;

import com.sasajankovic.domain.entities.Money;
import com.sasajankovic.domain.entities.route.AirlineCode;
import com.sasajankovic.domain.entities.route.AirlineId;
import com.sasajankovic.domain.entities.route.Route;
import com.sasajankovic.persistence.dao.RouteDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoutePersistenceMapper {
    private final AirportPersistenceMapper airportPersistenceMapper;

    public Route toDomainEntity(RouteDao route) {
        return new Route(
                route.getId(),
                AirlineId.create(Optional.ofNullable(route.getAirlineId())),
                AirlineCode.create(Optional.ofNullable(route.getAirlineCode())),
                airportPersistenceMapper.toDomainEntity(route.getSourceAirport()),
                airportPersistenceMapper.toDomainEntity(route.getDestinationAirport()),
                route.getCodeshare(),
                route.getNumberOfStops(),
                Arrays.asList(route.getAirlineCode().split(" ")),
                Money.create(route.getPrice()));
    }

    public RouteDao toPersistentEntity(Route route) {
        return RouteDao.builder()
                .id(route.getId())
                .airlineId(route.getAirlineId().map(AirlineId::get).orElse(null))
                .airlineCode(route.getAirlineCode().map(AirlineCode::get).orElse(null))
                .codeshare(route.isCodeshare())
                .numberOfStops(route.getNumberOfStops())
                .planeTypes(route.getPlaneTypes().stream().collect(Collectors.joining(" ")))
                .price(route.getPrice().get())
                .sourceAirport(
                        airportPersistenceMapper.toPersistentEntity(route.getSourceAirport()))
                .destinationAirport(
                        airportPersistenceMapper.toPersistentEntity(route.getDestinationAirport()))
                .build();
    }
}
