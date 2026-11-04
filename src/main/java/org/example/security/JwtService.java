package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.JwtProperties;
import org.example.entity.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
            if (keyBytes.length < 32) {
                keyBytes = jwtProperties.secret().getBytes(StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            keyBytes = jwtProperties.secret().getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return generateToken(user, null);
    }

    public String generateToken(User user, String workspaceId) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("username", user.getUsername());
        extraClaims.put("email", user.getEmail());
        extraClaims.put("role", user.getRole());
        if (workspaceId != null) {
            extraClaims.put("workspaceId", workspaceId);
        }

        return generateToken(extraClaims, String.valueOf(user.getId()));
    }

    /**
     * Token üretme metodu.
     * expirationMs <= 0 olduğunda issuedAt ve expiration eklenmez.
     * Böylece üretilen token hem süresiz olur hem de her çağrıda birebir aynı kalır.
     */
    public String generateToken(Map<String, Object> extraClaims, String subject) {
        var builder = Jwts.builder().claims(extraClaims).subject(subject).signWith(getSigningKey());

        if (jwtProperties.expirationMs() > 0) {
            long now = System.currentTimeMillis();
            builder.issuedAt(new Date(now)).expiration(new Date(now + jwtProperties.expirationMs()));
        }

        return builder.compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Geçersiz JWT Token: {}", e.getMessage());
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        Object userIdObj = claims.get("userId");
        if (userIdObj != null) {
            return Long.valueOf(userIdObj.toString());
        }
        return Long.valueOf(claims.getSubject());
    }

    public String extractWorkspaceId(String token) {
        Claims claims = extractAllClaims(token);
        Object wsId = claims.get("workspaceId");
        return wsId != null ? wsId.toString() : null;
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }
}
