package com.sasajankovic.domain.ports.in;

import com.sasajankovic.domain.exceptions.EntityNotFoundException;

public interface VerifyAccountUseCase {
    void verifyAccount(String token) throws EntityNotFoundException;
}
