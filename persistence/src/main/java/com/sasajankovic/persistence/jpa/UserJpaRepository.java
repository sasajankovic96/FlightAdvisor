package com.sasajankovic.persistence.jpa;

import com.sasajankovic.persistence.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserDao, Long> {
    Optional<UserDao> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
