package com.sasajankovic.persistence.dao;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "route")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RouteDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long airlineId;
    private String airlineCode;
    private Boolean codeshare;
    private Integer numberOfStops;
    private String planeTypes;
    private BigDecimal price;

    @ManyToOne private AirportDao sourceAirport;

    @ManyToOne private AirportDao destinationAirport;
}
