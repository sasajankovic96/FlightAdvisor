package com.sasajankovic.domain.ports.out;

import com.sasajankovic.domain.entities.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository {
    Optional<VerificationToken> findByToken(String token);

    void save(VerificationToken verificationToken);

    void delete(Long id);
}
