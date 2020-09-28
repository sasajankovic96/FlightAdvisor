package com.sasajankovic.persistence.repositories;

import com.sasajankovic.domain.entities.comments.Comment;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.ports.out.CommentRepository;
import com.sasajankovic.persistence.jpa.CommentJpaRepository;
import com.sasajankovic.persistence.mappers.CommentPersistenceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final CommentPersistenceMapper commentPersistenceMapper;
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Optional<Comment> findById(CommentId id) {
        return commentJpaRepository
                .findById(id.get())
                .map(commentPersistenceMapper::toDomainEntity);
    }

    @Override
    public void update(Comment comment) {
        commentJpaRepository.save(commentPersistenceMapper.toDaoEntity(comment));
    }
}
