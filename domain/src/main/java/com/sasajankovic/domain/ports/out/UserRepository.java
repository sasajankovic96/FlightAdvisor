package com.sasajankovic.domain.ports.out;

import com.sasajankovic.domain.entities.user.Email;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.entities.user.Username;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(Username username);

    boolean existsByUsername(Username username);

    boolean existsByEmail(Email email);

    User create(User user);

    User update(User user);
}
