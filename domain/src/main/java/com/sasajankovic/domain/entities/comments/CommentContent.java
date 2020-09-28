package com.sasajankovic.domain.entities.comments;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CommentContent {
    private static final int MAX_LENGTH = 1000;

    private final String content;

    public CommentContent(@NonNull String content) {
        if (content.isEmpty())
            throw new IllegalArgumentException("Comment content must not be empty");
        if (content.length() > MAX_LENGTH)
            throw new IllegalArgumentException(
                    String.format("Comment can't have more that %d characters", MAX_LENGTH));

        this.content = content;
    }

    @Override
    public int hashCode() {
        return 51 * content.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CommentContent)) return false;
        return ((CommentContent) obj).getContent().equals(this.content);
    }

    @Override
    public String toString() {
        return content;
    }
}
