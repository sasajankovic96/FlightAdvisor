package com.sasajankovic.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TokenService {
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    @Value("${token.appName}")
    private String appName;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiresIn}")
    private int expiresInSeconds;

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(now())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    public String getUsernameFromToken(String authToken) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            final Claims claims =
                    Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
            return claims.getExpiration().after(new Date());
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private Date now() {
        return new Date();
    }

    private Date generateExpirationDate() {
        return new Date(now().getTime() + expiresInSeconds * 1000);
    }
}
