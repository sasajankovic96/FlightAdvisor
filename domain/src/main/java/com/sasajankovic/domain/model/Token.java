package com.sasajankovic.domain.model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Token {
    private final String value;

    private Token(@NonNull String value) {
        this.value = value;
    }

    public static Token create(String value) {
        return new Token(value);
    }
}
