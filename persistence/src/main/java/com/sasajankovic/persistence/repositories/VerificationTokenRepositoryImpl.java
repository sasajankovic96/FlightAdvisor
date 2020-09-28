package com.sasajankovic.persistence.repositories;

import com.sasajankovic.domain.entities.VerificationToken;
import com.sasajankovic.domain.ports.out.VerificationTokenRepository;
import com.sasajankovic.persistence.jpa.VerificationTokenJpaRepository;
import com.sasajankovic.persistence.mappers.VerificationTokenPersistenceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {
    private final VerificationTokenJpaRepository verificationTokenJpaRepository;
    private final VerificationTokenPersistenceMapper verificationTokenPersistenceMapper;

    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenJpaRepository
                .findByToken(token)
                .map(verificationTokenPersistenceMapper::toDomainEntity);
    }

    public void save(VerificationToken verificationToken) {
        verificationTokenJpaRepository.save(
                verificationTokenPersistenceMapper.toPersistentEntity(verificationToken));
    }

    public void delete(Long id) {
        verificationTokenJpaRepository.deleteById(id);
    }
}
