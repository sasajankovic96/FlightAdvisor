package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.model.Credentials;
import com.sasajankovic.domain.model.Token;
import org.springframework.security.authentication.BadCredentialsException;

public interface LoginUserUseCase {
    Token login(Credentials credentials) throws BadCredentialsException;
}
