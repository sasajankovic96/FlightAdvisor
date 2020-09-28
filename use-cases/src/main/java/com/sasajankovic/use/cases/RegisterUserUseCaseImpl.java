package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.user.Password;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.entities.user.Username;
import com.sasajankovic.domain.exceptions.ConflictException;
import com.sasajankovic.domain.ports.in.RegisterUserUserCase;
import com.sasajankovic.domain.ports.out.UserRepository;
import com.sasajankovic.service.events.OnRegistrationCompleteEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class RegisterUserUseCaseImpl implements RegisterUserUserCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void registerUser(User user) throws ConflictException {
        boolean usernameTaken = userRepository.existsByUsername(Username.of(user.getUsername()));
        if (usernameTaken) {
            throw new ConflictException(
                    String.format("Username %s is already taken", user.getUsername()));
        }
        boolean emailTaken = userRepository.existsByEmail(user.getEmail());
        if (emailTaken) {
            throw new ConflictException(
                    String.format("Email %s is already taken", user.getEmail().toString()));
        }

        Password hashedPassword = new Password(passwordEncoder.encode(user.getPassword()));
        user.setPassword(hashedPassword);
        user = userRepository.create(user);

        log.info("Created new user with the username {}", user.getUsername());

        applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
    }
}
