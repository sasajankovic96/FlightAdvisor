package com.sasajankovic.persistence.dao;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VerificationToken")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VerificationTokenDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserDao user;
}
