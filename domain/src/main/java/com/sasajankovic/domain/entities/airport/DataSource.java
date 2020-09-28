package com.sasajankovic.domain.entities.airport;

import lombok.NonNull;

import java.util.Optional;

public class DataSource {
    private final String source;

    private DataSource(@NonNull String source) {
        this.source = source;
    }

    public static DataSource create(Optional<String> source) {
        return source.isPresent() ? new DataSource(source.get()) : null;
    }

    @Override
    public String toString() {
        return source;
    }
}
