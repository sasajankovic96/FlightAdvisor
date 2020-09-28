package com.sasajankovic.persistence.mappers;

import com.sasajankovic.domain.entities.city.*;
import com.sasajankovic.persistence.dao.CityDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CityPersistenceMapper {
    private final CommentPersistenceMapper commentPersistenceMapper;

    public City toDomainEntity(CityDao city) {
        return new City(
                new CityId(city.getId()),
                new CityName(city.getName()),
                new CityDescription(city.getDescription()),
                new Country(city.getCountry()),
                city.getComments().stream()
                        .map(commentPersistenceMapper::toDomainEntity)
                        .collect(Collectors.toList()));
    }

    public CityDao toPersistentEntity(City city) {
        Long id = Optional.ofNullable(city.getId()).map(CityId::get).orElse(null);
        return CityDao.builder()
                .id(id)
                .country(city.getCountry().getName())
                .name(city.getName().toString())
                .description(city.getDescription().toString())
                .comments(
                        city.getComments().stream()
                                .map(commentPersistenceMapper::toDaoEntity)
                                .collect(Collectors.toList()))
                .build();
    }
}
