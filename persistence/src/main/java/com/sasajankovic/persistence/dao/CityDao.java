package com.sasajankovic.persistence.dao;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CityDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String country;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CommentDao> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city")
    private List<AirportDao> airports;
}
