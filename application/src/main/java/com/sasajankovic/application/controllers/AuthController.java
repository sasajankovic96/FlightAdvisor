package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CreateUserDto;
import com.sasajankovic.application.dtos.CredentialsDto;
import com.sasajankovic.application.dtos.TokenDto;
import com.sasajankovic.application.dtos.VerificationTokenDto;
import com.sasajankovic.domain.entities.user.Password;
import com.sasajankovic.domain.entities.user.Username;
import com.sasajankovic.domain.model.Credentials;
import com.sasajankovic.domain.model.Token;
import com.sasajankovic.domain.ports.in.LoginUserUseCase;
import com.sasajankovic.domain.ports.in.RegisterUserUserCase;
import com.sasajankovic.domain.ports.in.VerifyAccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
public class AuthController {
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUserCase registerUserUserCase;
    private final VerifyAccountUseCase verifyAccountUseCase;

    @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public TokenDto login(@RequestBody @Valid CredentialsDto credentials) {
        Token token =
                loginUserUseCase.login(
                        new Credentials(
                                Username.of(credentials.getUsername()),
                                new Password(credentials.getPassword())));
        return new TokenDto(token.getValue());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserDto user) {
        registerUserUserCase.registerUser(user.toNewDomainEntity());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/account/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyAccount(@RequestBody @Valid VerificationTokenDto token) {
        verifyAccountUseCase.verifyAccount(token.getToken());
        return ResponseEntity.ok().build();
    }
}
