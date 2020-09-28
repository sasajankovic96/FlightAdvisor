package com.sasajankovic.application.dtos;

import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.route.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RouteDto {
    private String startCityName;
    private String startCountryName;
    private String startAirportName;
    private String destinationCityName;
    private String destinationCountryName;
    private String destinationAirportName;
    private String ticketPrice;

    public static RouteDto fromDomainEntity(Route route) {
        Airport sourceAirport = route.getSourceAirport();
        Airport destinationAirport = route.getDestinationAirport();
        return new RouteDto(
                sourceAirport.getCity().getName().toString(),
                sourceAirport.getCity().getCountry().toString(),
                sourceAirport.getName().toString(),
                destinationAirport.getCity().getName().toString(),
                destinationAirport.getCity().getCountry().toString(),
                destinationAirport.getName().toString(),
                route.getPrice().toString());
    }
}
