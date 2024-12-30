package org.example.bookstoremanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey; // Must be >= 32 chars for HS256 (256 bits)

    @Value("${jwt.expiration:86400000}") // Default to 1 day if not configured
    private long jwtExpirationInMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate a JWT token for the provided user.
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getUsername())         // Set subject (username)
                .claim("role", user.getRole())          // Add custom claims (role)
                .setIssuedAt(now)                       // Set token issue time
                .setExpiration(expiryDate)              // Set token expiration time
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign with HS256
                .compact();
    }

    /**
     * Validate the JWT token and log specific errors.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("Token expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Malformed JWT: {}", ex.getMessage());
        } catch (SignatureException ex) {
            log.error("Invalid signature: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token is null or empty: {}", ex.getMessage());
        }
        return false;
    }

    /**
     * Extract username from the token.
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Extract subject (username)
    }

    /**
     * Extract user role from the token.
     */
    public String getUserRoleFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class); // Extract role from claims
    }
}
