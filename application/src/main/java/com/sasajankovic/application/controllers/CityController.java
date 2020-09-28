package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CityDto;
import com.sasajankovic.application.dtos.CommentContentDto;
import com.sasajankovic.application.dtos.SearchCityParamsDto;
import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.ports.in.FetchCitiesUseCase;
import com.sasajankovic.domain.ports.in.PostCommentUseCase;
import com.sasajankovic.domain.ports.in.SearchCitiesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cities")
@AllArgsConstructor
@PreAuthorize("hasRole('REGULAR_USER')")
public class CityController {
    private final FetchCitiesUseCase fetchCitiesUseCase;
    private final PostCommentUseCase postCommentUseCase;
    private final SearchCitiesUseCase searchCitiesUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CityDto> fetchCities(@RequestParam Optional<Integer> numberOfComments) {
        List<City> cities = fetchCitiesUseCase.fetchCitiesWithComments(numberOfComments);
        return cities.stream().map(CityDto::fromDomainEntity).collect(Collectors.toList());
    }

    @PostMapping(
            value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CityDto> searchCities(@RequestBody @Valid SearchCityParamsDto requestBody) {
        return searchCitiesUseCase.searchCities(requestBody.toDomainModel()).stream()
                .map(CityDto::fromDomainEntity)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/{cityId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComment(
            @RequestBody @Valid CommentContentDto content,
            @PathVariable Long cityId,
            @AuthenticationPrincipal User user) {
        postCommentUseCase.postComment(
                new CommentContent(content.getContent()), new CityId(cityId), user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
