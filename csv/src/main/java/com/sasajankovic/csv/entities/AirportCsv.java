package com.sasajankovic.csv.entities;

import com.opencsv.bean.CsvBindByPosition;
import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import com.sasajankovic.domain.entities.airport.*;
import com.sasajankovic.domain.entities.city.City;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AirportCsv {

    @CsvBindByPosition(position = 0)
    private Long airportId;

    @CsvBindByPosition(position = 1)
    private String airportName;

    @CsvBindByPosition(position = 2)
    private String city;

    @CsvBindByPosition(position = 3)
    private String country;

    @CsvBindByPosition(position = 4)
    private String iataCode;

    @CsvBindByPosition(position = 5)
    private String icaoCode;

    @CsvBindByPosition(position = 6)
    private String latitude;

    @CsvBindByPosition(position = 7)
    private String longitude;

    @CsvBindByPosition(position = 8)
    private String altitude;

    @CsvBindByPosition(position = 9)
    private String timezoneOffset; // hours offset from UTC

    @CsvBindByPosition(position = 10)
    private String daylightSavingsTime;

    @CsvBindByPosition(position = 11)
    private String olsenFormatTimezone; // timezone in 'tz' (Olsen) format

    @CsvBindByPosition(position = 12)
    private String airportType;

    @CsvBindByPosition(position = 13)
    private String dataSource;

    public boolean isInTheCity(City city) {
        return city.getName().toString().equalsIgnoreCase(getCity())
                && city.getCountry().toString().equalsIgnoreCase(getCountry());
    }

    public Long getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Optional<String> getIataCode() {
        return hasValue(iataCode) ? Optional.of(iataCode) : Optional.empty();
    }

    public Optional<String> getIcaoCode() {
        return hasValue(icaoCode) ? Optional.of(icaoCode) : Optional.empty();
    }

    public Optional<Double> getLongitude() {
        return hasValue(longitude) ? Optional.of(Double.valueOf(longitude)) : Optional.empty();
    }

    public Optional<Double> getLatitude() {
        return hasValue(latitude) ? Optional.of(Double.valueOf(latitude)) : Optional.empty();
    }

    public Optional<Double> getAltitude() {
        return hasValue(altitude) ? Optional.of(Double.valueOf(altitude)) : Optional.empty();
    }

    public Optional<Integer> getTimezoneOffset() {
        return hasValue(timezoneOffset)
                ? Optional.of(Integer.parseInt(timezoneOffset))
                : Optional.empty();
    }

    public DaylightSavingsTime getDaylightSavingsTime() {
        return hasValue(daylightSavingsTime)
                ? DaylightSavingsTime.fromString(daylightSavingsTime)
                : DaylightSavingsTime.U;
    }

    public Optional<String> getOlsenFormatTimezone() {
        return hasValue(olsenFormatTimezone) ? Optional.of(olsenFormatTimezone) : Optional.empty();
    }

    public Optional<String> getAirportType() {
        return hasValue(airportType) ? Optional.of(airportType) : Optional.empty();
    }

    public Optional<String> getDataSource() {
        return hasValue(dataSource) ? Optional.of(dataSource) : Optional.empty();
    }

    private boolean hasValue(String param) {
        return !Objects.isNull(param)
                && !Strings.isBlank(param)
                && !param.trim().equals(FlightAdvisorConstants.NO_VALUE);
    }

    public Airport toDomainEntity(City city) {
        return new Airport(
                getAirportId(),
                AirportName.create(getAirportName()),
                city,
                AirportIataCode.create(getIataCode()),
                AirportIcaoCode.create(getIcaoCode()),
                Latitude.of(getLatitude()),
                Longitude.of(getLongitude()),
                Altitude.of(getAltitude()),
                TimezoneOffset.of(getTimezoneOffset()),
                getDaylightSavingsTime(),
                OlsonFormatTimezone.create(getOlsenFormatTimezone()),
                AirportType.create(getAirportType()),
                DataSource.create(getDataSource()));
    }
}
