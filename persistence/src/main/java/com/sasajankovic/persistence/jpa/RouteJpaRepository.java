package com.sasajankovic.persistence.jpa;

import com.sasajankovic.persistence.dao.RouteDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteJpaRepository extends JpaRepository<RouteDao, Long> {}
