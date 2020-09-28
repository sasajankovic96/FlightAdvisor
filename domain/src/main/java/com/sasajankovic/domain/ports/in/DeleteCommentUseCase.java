package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.User;

public interface DeleteCommentUseCase {
    void deleteComment(CommentId id, User loggedInUser);
}
