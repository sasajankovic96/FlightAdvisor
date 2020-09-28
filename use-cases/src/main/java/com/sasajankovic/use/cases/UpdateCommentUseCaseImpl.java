package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;
import com.sasajankovic.domain.ports.in.UpdateCommentUseCase;
import com.sasajankovic.domain.ports.out.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateCommentUseCaseImpl implements UpdateCommentUseCase {
    private final CommentRepository commentRepository;

    @Override
    public void updateComment(CommentId commentId, CommentContent newContent, User user) {
        Comment comment =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                String.format(
                                                        "No comment with id %d", commentId.get())));

        if (!comment.getUser().getId().equals(user.getId()))
            throw new ForbiddenException("Not enough permissions to update the comment");
        comment.updateContent(newContent);
        commentRepository.update(comment);
    }
}
