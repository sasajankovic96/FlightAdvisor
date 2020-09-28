package com.sasajankovic.persistence.dao;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "airport")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AirportDao {
    @Id private Long id;

    private String name;
    private String iataCode;
    private String icaoCode;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Integer utcOffset;
    private String daylightSavingsTime;
    private String olsonFormatTimezone;
    private String airportType;
    private String dataSource;

    @ManyToOne private CityDao city;
}
