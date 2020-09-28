package com.sasajankovic.persistence.mappers;

import com.sasajankovic.domain.entities.airport.*;
import com.sasajankovic.persistence.dao.AirportDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AirportPersistenceMapper {
    private final CityPersistenceMapper cityPersistenceMapper;

    public AirportDao toPersistentEntity(Airport airport) {
        return AirportDao.builder()
                .id(airport.getId())
                .name(airport.getName().toString())
                .iataCode(airport.getIataCode().map(AirportIataCode::toString).orElse(null))
                .icaoCode(airport.getIcaoCode().map(AirportIcaoCode::toString).orElse(null))
                .latitude(airport.getLatitude().map(Latitude::get).orElse(null))
                .longitude(airport.getLongitude().map(Longitude::get).orElse(null))
                .altitude(airport.getAltitude().map(Altitude::get).orElse(null))
                .utcOffset(airport.getTimezoneOffset().map(TimezoneOffset::get).orElse(null))
                .daylightSavingsTime(airport.getDaylightSavingsTime().toString())
                .olsonFormatTimezone(
                        airport.getTimezoneOlsenFormat()
                                .map(OlsonFormatTimezone::toString)
                                .orElse(null))
                .airportType(airport.getAirportType().map(AirportType::toString).orElse(null))
                .dataSource(airport.getDataSource().map(DataSource::toString).orElse(null))
                .city(cityPersistenceMapper.toPersistentEntity(airport.getCity()))
                .build();
    }

    public Airport toDomainEntity(AirportDao airportDao) {
        return new Airport(
                airportDao.getId(),
                AirportName.create(airportDao.getName()),
                cityPersistenceMapper.toDomainEntity(airportDao.getCity()),
                AirportIataCode.create(Optional.ofNullable(airportDao.getIataCode())),
                AirportIcaoCode.create(Optional.ofNullable(airportDao.getIcaoCode())),
                Latitude.of(Optional.ofNullable(airportDao.getLatitude())),
                Longitude.of(Optional.ofNullable(airportDao.getLongitude())),
                Altitude.of(Optional.ofNullable(airportDao.getAltitude())),
                TimezoneOffset.of(Optional.ofNullable(airportDao.getUtcOffset())),
                DaylightSavingsTime.fromString(airportDao.getDaylightSavingsTime()),
                OlsonFormatTimezone.create(
                        Optional.ofNullable(airportDao.getOlsonFormatTimezone())),
                AirportType.create(Optional.ofNullable(airportDao.getAirportType())),
                DataSource.create(Optional.ofNullable(airportDao.getDataSource())));
    }
}
