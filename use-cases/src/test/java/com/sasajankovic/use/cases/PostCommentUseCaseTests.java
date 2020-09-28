package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.user.*;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostCommentUseCaseTests {
    @Mock private CityRepository cityRepository;

    @InjectMocks private PostCommentUserCaseImpl postCommentUserCase;

    private CityId cityId = new CityId(1l);
    private City city;
    private User user;

    @BeforeEach
    public void setup() {
        city =
                new City(
                        cityId,
                        new CityName("Novi Sad"),
                        new CityDescription("An awesome town"),
                        new Country("Serbia"),
                        new ArrayList<>());
        user =
                new User(
                        1l,
                        new FirstName("John"),
                        new LastName("Smith"),
                        Username.of("john.smith"),
                        new Email("johnsmit@gmail.com"),
                        new Password("johnsmith!555"),
                        UserRole.REGULAR_USER,
                        true,
                        LocalDateTime.now().minusDays(5));
    }

    @Test
    public void ShouldUpdateTheComment() {
        Mockito.when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        CommentContent content = new CommentContent("This is new comment content");

        postCommentUserCase.postComment(content, cityId, user);

        Assertions.assertEquals(1, city.getComments().size());
        Assertions.assertEquals(content, city.getComments().get(0).getContent());
    }

    @Test
    public void PostingCommentToNonExistentCityShouldReturnNotFound() {
        Mockito.when(cityRepository.findById(cityId)).thenReturn(Optional.empty());
        CommentContent content = new CommentContent("This is new comment content");

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> postCommentUserCase.postComment(content, cityId, user));
        Assertions.assertEquals(0, city.getComments().size());
    }
}
