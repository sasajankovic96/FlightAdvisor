package com.sasajankovic.application.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.domain.entities.comments.Comment;

import java.io.IOException;
import java.util.List;

public class TestUtils {
    public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }

    public static City createCity(
            Long id, String name, String description, String country, List<Comment> comments) {
        return new City(
                new CityId(id),
                new CityName(name),
                new CityDescription(description),
                new Country(country),
                comments);
    }
}
