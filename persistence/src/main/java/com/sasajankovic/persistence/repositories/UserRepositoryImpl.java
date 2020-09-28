package com.sasajankovic.persistence.repositories;

import com.sasajankovic.domain.entities.user.Email;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.entities.user.Username;
import com.sasajankovic.domain.ports.out.UserRepository;
import com.sasajankovic.persistence.dao.UserDao;
import com.sasajankovic.persistence.jpa.UserJpaRepository;
import com.sasajankovic.persistence.mappers.UserPersistenceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    public Optional<User> findByUsername(Username username) {
        return userJpaRepository
                .findByUsername(username.toString())
                .map(userPersistenceMapper::toDomainEntity);
    }

    @Override
    public boolean existsByUsername(Username username) {
        return userJpaRepository.existsByUsername(username.toString());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userJpaRepository.existsByEmail(email.toString());
    }

    @Override
    public User create(User user) {
        UserDao userDao = userJpaRepository.save(userPersistenceMapper.toPersistentEntity(user));
        return userPersistenceMapper.toDomainEntity(userDao);
    }

    @Override
    public User update(User user) {
        UserDao userDao = userJpaRepository.save(userPersistenceMapper.toPersistentEntity(user));
        return userPersistenceMapper.toDomainEntity(userDao);
    }
}
