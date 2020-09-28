package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
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
public class FetchCitiesUseCaseTests {

    @Mock private CityRepository cityRepository;

    @InjectMocks private FetchCitiesUseCaseImpl fetchCitiesUseCase;

    private List<City> cities;
    private LocalDateTime newestCommentDate;

    @BeforeEach
    public void setup() {
        newestCommentDate = LocalDateTime.now();
        cities =
                Arrays.asList(
                        createCity(
                                1l,
                                "Novi Sad",
                                "Serbia",
                                "Awesome town",
                                Arrays.asList(
                                        createComment(
                                                1l, "Comment 1", LocalDateTime.now().minusDays(5)),
                                        createComment(2l, "Comment 2", newestCommentDate),
                                        createComment(
                                                3l,
                                                "Comment 3",
                                                LocalDateTime.now().minusDays(3)))),
                        createCity(
                                2l,
                                "Belgrade",
                                "Serbia",
                                "The capitol of Serbia",
                                Arrays.asList(createComment(4l, "Comment 4", newestCommentDate))),
                        createCity(
                                3l,
                                "Paris",
                                "France",
                                "The city of lights",
                                Collections.emptyList()));
    }

    @Test
    public void ShouldReturnCitiesWithAllComments() {
        Mockito.when(cityRepository.fetchCities()).thenReturn(cities);

        List<City> result = fetchCitiesUseCase.fetchCitiesWithComments(Optional.empty());

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(3, result.get(0).getComments().size());
        Assertions.assertEquals(1, result.get(1).getComments().size());
        Assertions.assertEquals(0, result.get(2).getComments().size());
        Assertions.assertEquals(
                newestCommentDate, result.get(0).getComments().get(0).getCreatedAt());
    }

    @Test
    public void ShouldReturnCitiesAndReturnOneCommentPerCity() {
        Mockito.when(cityRepository.fetchCities()).thenReturn(cities);

        List<City> result = fetchCitiesUseCase.fetchCitiesWithComments(Optional.of(1));

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(1, result.get(0).getComments().size());
        Assertions.assertEquals(1, result.get(1).getComments().size());
        Assertions.assertEquals(0, result.get(2).getComments().size());
        Assertions.assertEquals(
                newestCommentDate, result.get(0).getComments().get(0).getCreatedAt());
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

    private Comment createComment(Long id, String content, LocalDateTime createdAt) {
        return new Comment(
                new CommentId(id), new CommentContent(content), createdAt, createdAt, null);
    }
}
