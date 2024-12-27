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

    @Value("${jwt.expiration:86400000}") // optional: 1 day default
    private long jwtExpirationInMs;

    /**
     * (Optional) Generate JWT if you handle creation here,
     * or do it in your AuthService. Adjust as needed.
     */
    public String generateToken(User user) {
        // Requires at least a 256-bit key for HS256
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getUsername())         // subject = username
                .claim("role", user.getRole())          // store role if needed
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate the token signature and expiration.
     * Return boolean: true if valid, false if invalid.
     */
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            // If parsing works, token is valid
            return true;
        } catch (JwtException ex) {
            log.error("Token validation error: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * Extract the username (subject) from a valid token.
     */
    public String getUsernameFromJWT(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // the 'sub' field in JWT
    }
}
