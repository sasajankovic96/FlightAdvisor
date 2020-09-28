package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.model.Credentials;
import com.sasajankovic.domain.model.Token;
import com.sasajankovic.domain.ports.in.LoginUserUseCase;
import com.sasajankovic.domain.ports.out.UserRepository;
import com.sasajankovic.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginUserUseCaseImpl implements LoginUserUseCase {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public Token login(Credentials credentials) throws BadCredentialsException {
        User user =
                userRepository
                        .findByUsername(credentials.getUsername())
                        .orElseThrow(
                                () ->
                                        new BadCredentialsException(
                                                "No user find with the supplied credentials"));

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                credentials.getUsername().toString(),
                                credentials.getPassword().toString()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return Token.create(tokenService.generateToken(user.getUsername()));
    }
}
