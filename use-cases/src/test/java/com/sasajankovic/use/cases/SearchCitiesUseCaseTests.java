package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.model.SearchCityParams;
import com.sasajankovic.domain.ports.out.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SearchCitiesUseCaseTests {
    @Mock private CityRepository cityRepository;

    @InjectMocks private SearchCitiesUseCaseImpl searchCitiesUseCase;

    private List<City> cities;
    private LocalDateTime newestCommentDate;

    @Test
    public void ShouldFindNoCities() {
        SearchCityParams searchCityParams = new SearchCityParams("Madrid", Optional.of(2));
        Mockito.when(cityRepository.searchByName(new CityName(searchCityParams.getName())))
                .thenReturn(Collections.emptyList());

        List<City> result = searchCitiesUseCase.searchCities(searchCityParams);

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void ShouldFindCitiesWithAllComments() {
        SearchCityParams searchCityParams = new SearchCityParams("Be", Optional.empty());
        Mockito.when(cityRepository.searchByName(new CityName(searchCityParams.getName())))
                .thenReturn(cities);

        List<City> result = searchCitiesUseCase.searchCities(searchCityParams);

        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.get(0).getComments().size(), 3);
        Assertions.assertEquals(result.get(1).getComments().size(), 0);
        Assertions.assertEquals(
                result.get(0).getComments().get(0).getCreatedAt(), newestCommentDate);
    }

    @Test
    public void ShouldFindCitiesAndReturnOneCommentPerCity() {
        SearchCityParams searchCityParams = new SearchCityParams("Be", Optional.of(1));
        Mockito.when(cityRepository.searchByName(new CityName(searchCityParams.getName())))
                .thenReturn(cities);

        List<City> result = searchCitiesUseCase.searchCities(searchCityParams);

        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.get(0).getComments().size(), 1);
        Assertions.assertEquals(result.get(1).getComments().size(), 0);
        Assertions.assertEquals(
                result.get(0).getComments().get(0).getCreatedAt(), newestCommentDate);
    }

    @BeforeEach
    public void setup() {
        newestCommentDate = LocalDateTime.now();
        cities =
                Arrays.asList(
                        createCity(
                                1l,
                                "Belgrade",
                                "Serbia",
                                "The capitol of Serbia",
                                Arrays.asList(
                                        createComment(
                                                1l, "Comment 1", LocalDateTime.now().minusDays(5)),
                                        createComment(2l, "Comment 2", newestCommentDate),
                                        createComment(
                                                3l,
                                                "Comment 3",
                                                LocalDateTime.now().minusDays(3)))),
                        createCity(
                                3l,
                                "Berlin",
                                "Germany",
                                "The capitol city of Germany",
                                Collections.emptyList()));
    }

    private Comment createComment(Long id, String content, LocalDateTime createdAt) {
        return new Comment(
                new CommentId(id), new CommentContent(content), createdAt, createdAt, null);
    }

    private City createCity(
            Long id, String name, String country, String description, List<Comment> comments) {
        return new City(
                new CityId(id),
                new CityName(name),
                new CityDescription(description),
                new Country(country),
                comments);
    }
}
