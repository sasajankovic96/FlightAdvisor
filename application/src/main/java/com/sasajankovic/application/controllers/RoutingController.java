package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.RouteDto;
import com.sasajankovic.application.dtos.RoutePathDto;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.ports.in.FindShortestRouteUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("routes")
@AllArgsConstructor
@PreAuthorize("hasRole('REGULAR_USER')")
public class RoutingController {
    private final FindShortestRouteUseCase findShortestRouteUseCase;

    @PostMapping(
            value = "/find",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<RouteDto> findShortestRoute(@RequestBody @Valid RoutePathDto route) {
        return findShortestRouteUseCase
                .findShortestRoute(
                        new CityId(route.getSourceCityId()),
                        new CityId(route.getDestinationCityId()))
                .stream()
                .map(RouteDto::fromDomainEntity)
                .collect(Collectors.toList());
    }
}
