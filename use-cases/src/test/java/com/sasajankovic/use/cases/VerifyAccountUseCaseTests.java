package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.VerificationToken;
import com.sasajankovic.domain.entities.user.*;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;
import com.sasajankovic.domain.ports.out.UserRepository;
import com.sasajankovic.domain.ports.out.VerificationTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VerifyAccountUseCaseTests {

    @Mock private VerificationTokenRepository verificationTokenRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private VerifyAccountUserCaseImpl verifyAccountUserCase;

    private VerificationToken verificationToken;
    private User user;

    @BeforeEach
    public void setup() {
        user =
                User.createNewUser(
                        new FirstName("John"),
                        new LastName("Smith"),
                        new Email("john.smith@gmail.com"),
                        Username.of("johnsmit"),
                        new Password("john!smith5"));
        verificationToken =
                new VerificationToken(
                        1l, UUID.randomUUID().toString(), user, LocalDateTime.now().plusHours(2));
    }

    @Test
    public void ShouldNotActivateUserWhenTheVerificationTokenIsInvalid() {
        String token = UUID.randomUUID().toString();
        Mockito.when(verificationTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class, () -> verifyAccountUserCase.verifyAccount(token));
        Assertions.assertFalse(user.isEnabled());
    }

    @Test
    public void ShouldActivateUserWhenTheVerificationTokenIsValid() {
        String token = UUID.randomUUID().toString();
        Mockito.when(verificationTokenRepository.findByToken(token))
                .thenReturn(Optional.of(verificationToken));

        verifyAccountUserCase.verifyAccount(token);

        Assertions.assertTrue(user.isEnabled());
    }

    @Test
    public void ShouldNotActivateUserWhenTheVerificationTokenHasExpired() {
        String token = UUID.randomUUID().toString();
        verificationToken =
                new VerificationToken(1l, token, user, LocalDateTime.now().minusMinutes(5));

        Mockito.when(verificationTokenRepository.findByToken(token))
                .thenReturn(Optional.of(verificationToken));

        Assertions.assertThrows(
                ForbiddenException.class, () -> verifyAccountUserCase.verifyAccount(token));
        Assertions.assertFalse(user.isEnabled());
    }
}
