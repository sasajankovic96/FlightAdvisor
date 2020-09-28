package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.exceptions.ConflictException;

public interface RegisterUserUserCase {
    void registerUser(User user) throws ConflictException;
}
