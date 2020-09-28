package com.sasajankovic.persistence.jpa;

import com.sasajankovic.persistence.dao.CityDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityJpaRepository extends JpaRepository<CityDao, Long> {
    List<CityDao> findByNameStartsWithIgnoreCase(String name);

    Optional<CityDao> findByCommentsId(Long commentId);
}
