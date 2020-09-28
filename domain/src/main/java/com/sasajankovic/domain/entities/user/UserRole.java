package com.sasajankovic.domain.entities.user;

import java.util.Arrays;

public enum UserRole {
    ADMINISTRATOR("ADMINISTRATOR"),
    REGULAR_USER("REGULAR_USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public static UserRole fromString(String roleString) {
        return Arrays.asList(UserRole.values()).stream()
                .filter(userRole -> userRole.toString().equalsIgnoreCase(roleString))
                .findFirst()
                .orElseThrow(
                        () ->
                                new IllegalStateException(
                                        String.format(
                                                "%s can't be converted into UserRole",
                                                roleString)));
    }

    @Override
    public String toString() {
        return role;
    }
}
