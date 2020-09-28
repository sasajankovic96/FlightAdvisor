package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.user.*;
import com.sasajankovic.domain.model.Credentials;
import com.sasajankovic.domain.model.Token;
import com.sasajankovic.domain.ports.out.UserRepository;
import com.sasajankovic.service.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LoginUserUseCaseTests {

    @Mock private UserRepository userRepository;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private TokenService tokenService;

    @InjectMocks private LoginUserUseCaseImpl loginUserUseCase;

    @Test
    public void LoginShouldThrowBadCredentialsExceptionWhenUserDoesNotExist() {
        Username username = Username.of("dummyUser");
        Password password = new Password("DummyPassword!123");
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                BadCredentialsException.class,
                () -> loginUserUseCase.login(new Credentials(username, password)));
    }

    @Test
    public void ShouldLoginWithCorrectCredentials() {
        Username username = Username.of("dummyUser");
        Password password = new Password("DummyPassword!123");
        String token = UUID.randomUUID().toString();
        User user =
                new User(
                        1l,
                        new FirstName("Dummy"),
                        new LastName("User"),
                        username,
                        new Email("dummyuser@gmail.com"),
                        password,
                        UserRole.REGULAR_USER,
                        true,
                        LocalDateTime.now());
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(tokenService.generateToken(username.getUsername())).thenReturn(token);

        Token result = loginUserUseCase.login(new Credentials(username, password));

        Assertions.assertEquals(token, result.getValue());
    }
}
