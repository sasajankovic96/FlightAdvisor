package com.sasajankovic.domain.entities.airport;

import com.sasajankovic.domain.entities.city.City;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class Airport {
    private Long id;
    private AirportName name;
    private City city;
    private AirportIataCode iataCode;
    private AirportIcaoCode icaoCode;
    private Latitude latitude;
    private Longitude longitude;
    private Altitude altitude;
    private TimezoneOffset offset;
    private DaylightSavingsTime daylightSavingsTime;
    private OlsonFormatTimezone timezone;
    private AirportType airportType;
    private DataSource dataSource;

    public Airport(
            Long id,
            AirportName name,
            City city,
            AirportIataCode iataCode,
            AirportIcaoCode icaoCode,
            Latitude latitude,
            Longitude longitude,
            Altitude altitude,
            TimezoneOffset offset,
            DaylightSavingsTime daylightSavingsTime,
            OlsonFormatTimezone timezone,
            AirportType airportType,
            DataSource dataSource) {
        if (Objects.isNull(iataCode) && Objects.isNull(icaoCode)) {
            throw new IllegalArgumentException("Airport must have either IATA code or ICAO code");
        }
        this.id = id;
        this.name = name;
        this.city = city;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.offset = offset;
        this.daylightSavingsTime = daylightSavingsTime;
        this.timezone = timezone;
        this.airportType = airportType;
        this.dataSource = dataSource;
    }

    public Optional<AirportIcaoCode> getIcaoCode() {
        return Optional.ofNullable(icaoCode);
    }

    public Optional<AirportIataCode> getIataCode() {
        return Optional.ofNullable(iataCode);
    }

    public Optional<Latitude> getLatitude() {
        return Optional.ofNullable(latitude);
    }

    public Optional<Longitude> getLongitude() {
        return Optional.ofNullable(longitude);
    }

    public Optional<Altitude> getAltitude() {
        return Optional.ofNullable(altitude);
    }

    public Optional<TimezoneOffset> getTimezoneOffset() {
        return Optional.ofNullable(offset);
    }

    public Optional<OlsonFormatTimezone> getTimezoneOlsenFormat() {
        return Optional.ofNullable(timezone);
    }

    public Optional<AirportType> getAirportType() {
        return Optional.ofNullable(airportType);
    }

    public Optional<DataSource> getDataSource() {
        return Optional.ofNullable(dataSource);
    }

    public boolean inTheCity(City city) {
        return this.city.equals(city);
    }

    public boolean equalsByAirportId(Optional<Long> id) {
        return id.isPresent() && this.id.equals(id.get());
    }

    public boolean equalsByAirportCode(Optional<String> code) {
        return code.isPresent() && (equalsByIataCode(code.get()) || equalsByIcaoCode(code.get()));
    }

    private boolean equalsByIcaoCode(String code) {
        return getIcaoCode().map(icaoCode -> icaoCode.toString().equals(code)).orElse(false);
    }

    private boolean equalsByIataCode(String code) {
        return getIataCode().map(iataCode -> iataCode.toString().equals(code)).orElse(false);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Airport)) {
            return false;
        }
        Airport airport = (Airport) obj;
        return airport.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(id);
    }
}
