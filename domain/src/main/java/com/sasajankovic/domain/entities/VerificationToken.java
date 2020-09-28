package com.sasajankovic.domain.entities;

import com.sasajankovic.domain.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class VerificationToken {
    private static final int EXPIRES_IN_SECONDS = 7200;
    private Long id;
    private String token;
    private User user;
    private LocalDateTime expiryDate;

    public static VerificationToken create(@NonNull User user) {
        String token =
                Base64.getUrlEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(EXPIRES_IN_SECONDS);
        return new VerificationToken(null, token, user, expiryDate);
    }

    public boolean hasExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    @Override
    public String toString() {
        return token;
    }
}
