package com.sasajankovic.persistence.jpa;

import com.sasajankovic.persistence.dao.CommentDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentDao, Long> {}
