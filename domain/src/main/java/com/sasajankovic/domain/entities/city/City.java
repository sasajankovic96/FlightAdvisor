package com.sasajankovic.domain.entities.city;

import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class City {
    private CityId id;
    private CityName name;
    private CityDescription description;
    private Country country;
    private List<Comment> comments;

    public static City createNewCity(CityName name, CityDescription description, Country country) {
        return new City(null, name, description, country, Collections.EMPTY_LIST);
    }

    public City withSortedLatestComments(int numberOfComments) {
        comments =
                comments.stream()
                        .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                        .limit(numberOfComments)
                        .collect(Collectors.toList());
        return new City(id, name, description, country, comments);
    }

    public City withSortedComments() {
        comments =
                comments.stream()
                        .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                        .collect(Collectors.toList());
        return new City(id, name, description, country, comments);
    }

    public void addComment(CommentContent commentContent, User commentWriter) {
        comments.add(Comment.createNewComment(commentContent, commentWriter));
    }

    public void removeComment(CommentId commentId) {
        this.comments =
                comments.stream()
                        .filter(comment -> !comment.getId().equals(commentId))
                        .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        return 41 * id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof City)) return false;
        City city = (City) obj;
        return city.getId().equals(id);
    }
}
