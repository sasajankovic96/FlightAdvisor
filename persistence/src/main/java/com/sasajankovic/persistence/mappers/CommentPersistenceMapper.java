package com.sasajankovic.persistence.mappers;

import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.persistence.dao.CommentDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentPersistenceMapper {
    private final UserPersistenceMapper userPersistenceMapper;

    public Comment toDomainEntity(CommentDao comment) {
        return new Comment(
                new CommentId(comment.getId()),
                new CommentContent(comment.getContent()),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                userPersistenceMapper.toDomainEntity(comment.getUser()));
    }

    public CommentDao toDaoEntity(Comment comment) {
        return CommentDao.builder()
                .id(Optional.ofNullable(comment.getId()).map(CommentId::get).orElse(null))
                .content(comment.getContent().toString())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .user(userPersistenceMapper.toPersistentEntity(comment.getUser()))
                .build();
    }
}
