package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;
import com.sasajankovic.domain.ports.in.DeleteCommentUseCase;
import com.sasajankovic.domain.ports.out.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteCommentUseCaseImpl implements DeleteCommentUseCase {
    private final CityRepository cityRepository;

    @Override
    public void deleteComment(CommentId id, User loggedInUser) {
        City city =
                cityRepository
                        .findByCommentId(id)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                String.format(
                                                        "Comment with id %d not found", id.get())));
        Comment comment =
                city.getComments().stream().filter(c -> c.getId().equals(id)).findFirst().get();
        if (!comment.getUser().getId().equals(loggedInUser.getId()))
            throw new ForbiddenException("Not enough permissions to delete the comment");
        city.removeComment(id);
        cityRepository.update(city);
    }
}
