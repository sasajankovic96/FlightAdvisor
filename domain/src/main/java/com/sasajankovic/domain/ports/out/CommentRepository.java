package com.sasajankovic.domain.ports.out;

import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentId;

import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(CommentId id);

    void update(Comment comment);
}
