package com.sasajankovic.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoutePathDto {
    @NotNull(message = "Source city must not be empty")
    private Long sourceCityId;

    @NotNull(message = "Destination city must not be empty")
    private Long destinationCityId;
}
