package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;

public interface PostCommentUseCase {
    void postComment(CommentContent content, CityId cityId, User user)
            throws EntityNotFoundException;
}
