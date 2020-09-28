package com.sasajankovic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    private static final String NO_REPLY_ADDRESS = "noreply@flightadvisor.com";

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NO_REPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
        } catch (MailException e) {
            log.warn(
                    "Failed to send an email to {} with subject {} and content {} with exception message {}",
                    to,
                    subject,
                    content,
                    e.getMessage());
        }
    }
}
