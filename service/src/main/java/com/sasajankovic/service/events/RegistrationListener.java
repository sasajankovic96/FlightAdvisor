package com.sasajankovic.service.events;

import com.sasajankovic.domain.entities.VerificationToken;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.ports.out.VerificationTokenRepository;
import com.sasajankovic.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private static final String EMAIL_TEMPLATE_MESSAGE =
            "Welcome to Flight Advisor app. Please confirm your account by posting the following token: %s";
    private static final String EMAIL_SUBJECT = "Registration Confirmation";

    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        VerificationToken token = VerificationToken.create(user);
        verificationTokenRepository.save(token);

        String message = String.format(EMAIL_TEMPLATE_MESSAGE, token.getToken());
        emailService.sendEmail(user.getEmail().toString(), EMAIL_SUBJECT, message);
    }
}
