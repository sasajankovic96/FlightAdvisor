package com.sasajankovic.domain.entities.route;

import com.sasajankovic.domain.entities.Money;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.city.City;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor()
public class Route {
    private Long id;
    private AirlineId airlineId;
    private AirlineCode airlineCode;
    private Airport sourceAirport;
    private Airport destinationAirport;
    private boolean codeshare;
    private int numberOfStops;
    private List<String> planeTypes;
    private Money price;

    public Optional<AirlineId> getAirlineId() {
        return Optional.ofNullable(airlineId);
    }

    public Optional<AirlineCode> getAirlineCode() {
        return Optional.ofNullable(airlineCode);
    }

    public City getSourceCity() {
        return sourceAirport.getCity();
    }

    public City getDestinationCity() {
        return destinationAirport.getCity();
    }

    public String getRoutePathIdentifier() {
        return sourceAirport.getId().toString().concat(destinationAirport.getId().toString());
    }
}
