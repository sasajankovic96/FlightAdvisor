package com.sasajankovic.persistence.mappers;

import com.sasajankovic.domain.entities.user.*;
import com.sasajankovic.persistence.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {
    public User toDomainEntity(UserDao user) {
        return new User(
                user.getId(),
                new FirstName(user.getFirstName()),
                new LastName(user.getLastName()),
                Username.of(user.getUsername()),
                new Email(user.getEmail()),
                new Password(user.getPassword()),
                UserRole.fromString(user.getRole()),
                user.isEnabled(),
                user.getCreatedAt());
    }

    public UserDao toPersistentEntity(User user) {
        return UserDao.builder()
                .id(user.getId())
                .firstName(user.getFirstName().toString())
                .lastName(user.getLastName().toString())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail().toString())
                .role(user.getRole().toString())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
