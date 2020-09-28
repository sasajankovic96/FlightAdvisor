package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.*;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteCommentUseCaseTest {
    @Mock private CityRepository cityRepository;

    @InjectMocks private DeleteCommentUseCaseImpl deleteCommentUseCase;

    private User user;
    private City city;
    private CommentId commentId;

    @BeforeEach
    public void setup() {
        commentId = new CommentId(1l);
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

        Comment comment =
                new Comment(
                        commentId,
                        new CommentContent("Comment content"),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        user);

        city =
                new City(
                        new CityId(1l),
                        new CityName("Berlin"),
                        new CityDescription("City description"),
                        new Country("Germany"),
                        Arrays.asList(comment));
    }

    @Test
    public void ShouldDeleteTheComment() {
        Mockito.when(cityRepository.findByCommentId(commentId)).thenReturn(Optional.of(city));

        deleteCommentUseCase.deleteComment(commentId, user);

        Assertions.assertEquals(0, city.getComments().size());
    }

    @Test
    public void ShouldReturnNotFoundWhenTheCommentDoesNotExist() {
        Mockito.when(cityRepository.findByCommentId(commentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> deleteCommentUseCase.deleteComment(commentId, user));
    }

    @Test
    public void ShouldReturnForbiddenWhenTheUserIsNotTheWriterOfTheComment() {
        Mockito.when(cityRepository.findByCommentId(commentId)).thenReturn(Optional.of(city));
        User loggedInUser =
                new User(
                        2l,
                        new FirstName("Jack"),
                        new LastName("White"),
                        Username.of("jackwhite"),
                        new Email("jack@gmail.com"),
                        new Password("jackwhite!123"),
                        UserRole.REGULAR_USER,
                        true,
                        LocalDateTime.now().minusDays(5));

        Assertions.assertThrows(
                ForbiddenException.class,
                () -> deleteCommentUseCase.deleteComment(commentId, loggedInUser));
    }
}
