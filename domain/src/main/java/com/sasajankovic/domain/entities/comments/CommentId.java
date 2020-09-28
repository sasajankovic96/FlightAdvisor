package com.sasajankovic.domain.entities.comments;

import lombok.NonNull;

public class CommentId {
    private final Long id;

    public CommentId(@NonNull Long id) {
        this.id = id;
    }

    public Long get() {
        return id;
    }

    @Override
    public int hashCode() {
        return 17 * id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CommentId)) return false;
        return ((CommentId) obj).get().equals(id);
    }
}
