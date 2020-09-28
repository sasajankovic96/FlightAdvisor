package com.sasajankovic.application.dtos;

import com.sasajankovic.domain.model.SearchCityParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchCityParamsDto {
    @NotBlank(message = "Name must not be empty")
    private String name;

    @Min(value = 0, message = "Number of comments must not either a positive number or zero")
    private Integer numberOfComments;

    public SearchCityParams toDomainModel() {
        return new SearchCityParams(name, Optional.ofNullable(numberOfComments));
    }
}
