package com.sasajankovic.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityDescription;
import com.sasajankovic.domain.entities.city.CityName;
import com.sasajankovic.domain.entities.city.Country;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "City name must not be empty")
    private String name;

    @NotBlank(message = "City description must not be empty")
    private String description;

    @NotBlank(message = "Country must not be empty")
    private String country;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CommentDto> comments;

    public static CityDto fromDomainEntity(City city) {
        return CityDto.builder()
                .id(city.getId().get())
                .name(city.getName().toString())
                .description(city.getDescription().toString())
                .country(city.getCountry().toString())
                .comments(
                        city.getComments().stream()
                                .map(CommentDto::fromDomainEntity)
                                .collect(Collectors.toList()))
                .build();
    }

    public City toDomainEntity() {
        return City.createNewCity(
                new CityName(name), new CityDescription(description), new Country(country));
    }
}
