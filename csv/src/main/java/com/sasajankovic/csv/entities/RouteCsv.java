package com.sasajankovic.csv.entities;

import com.opencsv.bean.CsvBindByPosition;
import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import com.sasajankovic.domain.entities.Money;
import com.sasajankovic.domain.entities.airport.Airport;
import com.sasajankovic.domain.entities.route.AirlineCode;
import com.sasajankovic.domain.entities.route.AirlineId;
import com.sasajankovic.domain.entities.route.Route;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RouteCsv {
    @CsvBindByPosition(position = 0)
    private String airlineCode;

    @CsvBindByPosition(position = 1)
    private String airlineId;

    @CsvBindByPosition(position = 2)
    private String sourceAirportCode;

    @CsvBindByPosition(position = 3)
    private String sourceAirportId;

    @CsvBindByPosition(position = 4)
    private String destinationAirportCode;

    @CsvBindByPosition(position = 5)
    private String destinationAirportId;

    @CsvBindByPosition(position = 6)
    private String codeshare; // 'Y' if it is codeshare else null

    @CsvBindByPosition(position = 7)
    private String numberOfStops;

    @CsvBindByPosition(position = 8)
    private String airplaneTypes;

    @CsvBindByPosition(position = 9)
    private BigDecimal price;

    public Optional<String> getAirlineCode() {
        return hasValue(airlineCode) ? Optional.of(airlineCode) : Optional.empty();
    }

    public Optional<Long> getAirlineId() {
        return hasValue(airlineId) ? Optional.of(Long.valueOf(airlineId)) : Optional.empty();
    }

    public Optional<String> getSourceAirportCode() {
        return hasValue(sourceAirportCode) ? Optional.of(sourceAirportCode) : Optional.empty();
    }

    public Optional<Long> getSourceAirportId() {
        return hasValue(sourceAirportId)
                ? Optional.of(Long.valueOf(sourceAirportId))
                : Optional.empty();
    }

    public Optional<String> getDesignationAirportCode() {
        return hasValue(destinationAirportCode)
                ? Optional.of(destinationAirportCode)
                : Optional.empty();
    }

    public Optional<Long> getDestinationAirportId() {
        return hasValue(destinationAirportId)
                ? Optional.of(Long.valueOf(destinationAirportId))
                : Optional.empty();
    }

    public boolean isCodeshare() {
        return hasValue(codeshare) && codeshare.trim().equals("Y");
    }

    public int getNumberOfStops() {
        return hasValue(numberOfStops) ? Integer.parseInt(numberOfStops) : 0;
    }

    public List<String> getPlaneTypes() {
        return hasValue(airplaneTypes)
                ? Arrays.asList(airplaneTypes.split(" "))
                : Collections.EMPTY_LIST;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private boolean hasValue(String param) {
        return !Objects.isNull(param)
                && !Strings.isBlank(param)
                && !param.trim().equals(FlightAdvisorConstants.NO_VALUE);
    }

    public Route toDomainEntity(Airport sourceAirport, Airport destinationAirport) {
        return new Route(
                null,
                AirlineId.create(getAirlineId()),
                AirlineCode.create(getAirlineCode()),
                sourceAirport,
                destinationAirport,
                isCodeshare(),
                getNumberOfStops(),
                getPlaneTypes(),
                Money.create(getPrice()));
    }
}
