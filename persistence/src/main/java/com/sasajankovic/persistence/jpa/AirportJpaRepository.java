package com.sasajankovic.persistence.jpa;

import com.sasajankovic.persistence.dao.AirportDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportJpaRepository extends JpaRepository<AirportDao, Long> {}
