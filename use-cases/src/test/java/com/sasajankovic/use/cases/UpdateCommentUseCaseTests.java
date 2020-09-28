package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.*;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;
import com.sasajankovic.domain.ports.out.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateCommentUseCaseTests {
    @Mock private CommentRepository commentRepository;

    @InjectMocks private UpdateCommentUseCaseImpl updateCommentUseCase;

    private Comment comment;
    private User commentWriter;
    private CommentContent commentContent;
    private CommentId commentId;

    @BeforeEach
    public void setup() {
        LocalDateTime commentCreatedAt = LocalDateTime.now().minusDays(5);
        commentContent = new CommentContent("This is old content");
        commentId = new CommentId(1l);
        commentWriter =
                new User(
                        1l,
                        new FirstName("John"),
                        new LastName("Smith"),
                        Username.of("johnsmith"),
                        new Email("john.smith@gmail.com"),
                        new Password("john!smith5"),
                        UserRole.REGULAR_USER,
                        true,
                        LocalDateTime.now().minusDays(10));
        comment =
                new Comment(
                        commentId,
                        commentContent,
                        commentCreatedAt,
                        commentCreatedAt,
                        commentWriter);
    }

    @Test
    public void ShouldUpdateComment() {
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        CommentContent updatedCommentContent = new CommentContent("This is new content");

        updateCommentUseCase.updateComment(commentId, updatedCommentContent, commentWriter);

        Assertions.assertEquals(updatedCommentContent, comment.getContent());
        Assertions.assertNotEquals(comment.getCreatedAt(), comment.getModifiedAt());
    }

    @Test
    public void ShouldReturnNotFoundWhenCommentDoesNotExist() {
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () ->
                        updateCommentUseCase.updateComment(
                                commentId, new CommentContent("New content"), commentWriter));
        Assertions.assertEquals(commentContent, comment.getContent());
        Assertions.assertTrue(comment.getCreatedAt().isEqual(comment.getModifiedAt()));
    }

    @Test
    public void ShouldReturnForbiddenWhenTheUserIsNotTheWriterOfTheComment() {
        User user =
                new User(
                        2l,
                        new FirstName("Jack"),
                        new LastName("White"),
                        Username.of("jackwhite"),
                        new Email("jack.white@gmail.com"),
                        new Password("jack!white5"),
                        UserRole.REGULAR_USER,
                        true,
                        LocalDateTime.now().minusDays(10));
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Assertions.assertThrows(
                ForbiddenException.class,
                () ->
                        updateCommentUseCase.updateComment(
                                commentId, new CommentContent("New content"), user));
        Assertions.assertEquals(commentContent, comment.getContent());
        Assertions.assertTrue(comment.getCreatedAt().isEqual(comment.getModifiedAt()));
    }
}
