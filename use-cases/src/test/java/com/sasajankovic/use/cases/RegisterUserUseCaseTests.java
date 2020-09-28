package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.user.*;
import com.sasajankovic.domain.exceptions.ConflictException;
import com.sasajankovic.domain.ports.out.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTests {
    @Mock private UserRepository userRepository;

    @Mock private PasswordEncoder passwordEncoder;

    @Mock private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks private RegisterUserUseCaseImpl registerUserUseCase;

    private User user;

    @BeforeEach
    public void setup() {
        user =
                new User(
                        1l,
                        new FirstName("John"),
                        new LastName("Smith"),
                        Username.of("john.smith"),
                        new Email("johnsmit@gmail.com"),
                        new Password("johnsmith!555"),
                        UserRole.REGULAR_USER,
                        false,
                        null);
    }

    @Test
    public void ShouldThrowConflictExceptionWhenUsernameIsTaken() {
        Mockito.when(userRepository.existsByUsername(Username.of("john.smith"))).thenReturn(true);

        Assertions.assertThrows(
                ConflictException.class, () -> registerUserUseCase.registerUser(user));
    }

    @Test
    public void ShouldThrowConflictExceptionWhenEmailIsTaken() {
        Mockito.when(userRepository.existsByEmail(new Email("johnsmit@gmail.com")))
                .thenReturn(true);

        Assertions.assertThrows(
                ConflictException.class, () -> registerUserUseCase.registerUser(user));
    }
}
