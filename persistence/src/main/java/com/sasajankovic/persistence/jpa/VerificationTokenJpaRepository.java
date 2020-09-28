package com.sasajankovic.persistence.jpa;

import com.sasajankovic.persistence.dao.VerificationTokenDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenJpaRepository extends JpaRepository<VerificationTokenDao, Long> {
    Optional<VerificationTokenDao> findByToken(String token);
}
