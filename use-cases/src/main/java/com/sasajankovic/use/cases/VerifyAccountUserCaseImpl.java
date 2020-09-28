package com.sasajankovic.use.cases;

import com.sasajankovic.domain.entities.VerificationToken;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;
import com.sasajankovic.domain.ports.in.VerifyAccountUseCase;
import com.sasajankovic.domain.ports.out.UserRepository;
import com.sasajankovic.domain.ports.out.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VerifyAccountUserCaseImpl implements VerifyAccountUseCase {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void verifyAccount(String token) throws EntityNotFoundException, ForbiddenException {
        VerificationToken verificationToken =
                verificationTokenRepository
                        .findByToken(token)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Verification token not found"));
        if (verificationToken.hasExpired())
            throw new ForbiddenException("Verification token has expired");
        User user = verificationToken.getUser();
        user.activate();
        userRepository.update(user);
        verificationTokenRepository.delete(verificationToken.getId());
    }
}
