package com.sasajankovic.persistence.repositories;

import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.entities.city.CityName;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.ports.out.CityRepository;
import com.sasajankovic.persistence.jpa.CityJpaRepository;
import com.sasajankovic.persistence.mappers.CityPersistenceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityRepositoryImpl implements CityRepository {
    private final CityJpaRepository cityJpaRepository;
    private final CityPersistenceMapper cityPersistenceMapper;

    @Override
    public CityId create(City city) {
        return cityPersistenceMapper
                .toDomainEntity(
                        cityJpaRepository.save(cityPersistenceMapper.toPersistentEntity(city)))
                .getId();
    }

    @Override
    public CityId update(City city) {
        return cityPersistenceMapper
                .toDomainEntity(
                        cityJpaRepository.save(cityPersistenceMapper.toPersistentEntity(city)))
                .getId();
    }

    @Override
    public List<City> fetchCities() {
        return cityJpaRepository.findAll().stream()
                .map(cityPersistenceMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<City> findById(CityId cityId) {
        return cityJpaRepository.findById(cityId.get()).map(cityPersistenceMapper::toDomainEntity);
    }

    @Override
    public List<City> searchByName(CityName name) {
        return cityJpaRepository.findByNameStartsWithIgnoreCase(name.toString()).stream()
                .map(cityPersistenceMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<City> findByCommentId(CommentId commentId) {
        return cityJpaRepository
                .findByCommentsId(commentId.get())
                .map(cityPersistenceMapper::toDomainEntity);
    }
}
