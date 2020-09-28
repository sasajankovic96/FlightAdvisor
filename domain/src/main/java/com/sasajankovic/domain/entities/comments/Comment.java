package com.sasajankovic.domain.entities.comments;

import com.sasajankovic.domain.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Comment {
    private CommentId id;
    private CommentContent content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private User user;

    public static Comment createNewComment(CommentContent content, User commentWriter) {
        LocalDateTime currentTime = LocalDateTime.now();
        return new Comment(null, content, currentTime, currentTime, commentWriter);
    }

    public void updateContent(CommentContent content) {
        this.content = content;
        modifiedAt = LocalDateTime.now();
    }
}
