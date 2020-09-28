package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.ports.in.PostCommentUseCase;
import com.sasajankovic.domain.ports.out.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostCommentUserCaseImpl implements PostCommentUseCase {
    private final CityRepository cityRepository;

    @Override
    public void postComment(CommentContent content, CityId cityId, User user) {
        City city =
                cityRepository
                        .findById(cityId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                String.format(
                                                        "Could not find city with id %d",
                                                        cityId.get())));
        city.addComment(content, user);
        cityRepository.update(city);
    }
}
