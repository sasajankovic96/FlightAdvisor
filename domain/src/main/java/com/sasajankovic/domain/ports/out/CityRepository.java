package com.sasajankovic.domain.ports.out;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.entities.city.CityName;
import com.sasajankovic.domain.entities.comments.CommentId;

import java.util.List;
import java.util.Optional;

public interface CityRepository {
    CityId create(City city);

    CityId update(City city);

    List<City> fetchCities();

    Optional<City> findById(CityId cityId);

    List<City> searchByName(CityName name);

    Optional<City> findByCommentId(CommentId commentId);
}
