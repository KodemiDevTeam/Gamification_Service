package com.gamification.streaks.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    // Must be at least 256 bits (32 chars) for HMAC-SHA256
    private static final String SECRET = "test-secret-key-that-is-long-enough-for-hmac-sha256";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Inject the secret via ReflectionTestUtils since it's @Value injected
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
    }

    private String buildToken(String userId, String role) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String buildExpiredToken(String userId, String role) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200_000))
                .setExpiration(new Date(System.currentTimeMillis() - 3600_000)) // already expired
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    void validateToken_shouldReturnTrue_forValidToken() {
        String token = buildToken("user-1", "TRAINER");

        assertThat(jwtUtil.validateToken(token)).isTrue();
    }

    @Test
    void validateToken_shouldReturnFalse_forExpiredToken() {
        String token = buildExpiredToken("user-1", "TRAINER");

        assertThat(jwtUtil.validateToken(token)).isFalse();
    }

    @Test
    void validateToken_shouldReturnFalse_forInvalidToken() {
        assertThat(jwtUtil.validateToken("this.is.not.a.valid.token")).isFalse();
    }

    @Test
    void extractUserId_shouldReturnCorrectSubject() {
        String token = buildToken("user-42", "LEARNER");

        assertThat(jwtUtil.extractUserId(token)).isEqualTo("user-42");
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {
        String token = buildToken("user-1", "TRAINER");

        assertThat(jwtUtil.extractRole(token)).isEqualTo("TRAINER");
    }

    @Test
    void extractRole_shouldReturnLearnerRole() {
        String token = buildToken("user-2", "LEARNER");

        assertThat(jwtUtil.extractRole(token)).isEqualTo("LEARNER");
    }
}
