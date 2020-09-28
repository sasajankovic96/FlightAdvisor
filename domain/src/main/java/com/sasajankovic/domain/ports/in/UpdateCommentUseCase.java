package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;

public interface UpdateCommentUseCase {
    void updateComment(CommentId commentId, CommentContent newContent, User user)
            throws EntityNotFoundException, ForbiddenException;
}
