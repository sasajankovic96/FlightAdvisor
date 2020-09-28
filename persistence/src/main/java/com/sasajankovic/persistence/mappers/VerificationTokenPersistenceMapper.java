package com.sasajankovic.persistence.mappers;

import com.sasajankovic.domain.entities.VerificationToken;
import com.sasajankovic.persistence.dao.VerificationTokenDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VerificationTokenPersistenceMapper {
    private final UserPersistenceMapper userPersistenceMapper;

    public VerificationToken toDomainEntity(VerificationTokenDao verificationToken) {
        return new VerificationToken(
                verificationToken.getId(),
                verificationToken.getToken(),
                userPersistenceMapper.toDomainEntity(verificationToken.getUser()),
                verificationToken.getExpiryDate());
    }

    public VerificationTokenDao toPersistentEntity(VerificationToken verificationToken) {
        return VerificationTokenDao.builder()
                .id(verificationToken.getId())
                .token(verificationToken.getToken())
                .expiryDate(verificationToken.getExpiryDate())
                .user(userPersistenceMapper.toPersistentEntity(verificationToken.getUser()))
                .build();
    }
}
