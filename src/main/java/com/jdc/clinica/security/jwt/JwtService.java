package com.jdc.clinica.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // ==========================================================
    // CONFIGURACIÓN DEL TOKEN
    // ==========================================================
    private final String SECRET = "MI_CLAVE_SECRETA_256_BITS_SEGURA_PARA_JWT_123456789"; // ≥ 32 chars
    private final Long EXPIRATION = 1000 * 60 * 60 * 10L; // 10 horas

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ==========================================================
    // 🔥 GENERAR TOKEN (email + rol + iduser)
    // ==========================================================
    public String generateToken(Long id, String email, String rol) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("iduser", id);
        claims.put("email", email);
        claims.put("rol", rol);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // opcional
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ==========================================================
    // OBTENER DATOS DEL TOKEN
    // ==========================================================
    public String extractEmail(String token) {
        return parseToken(token).getBody().get("email", String.class);
    }

    public Integer extractId(String token) {
        return parseToken(token).getBody().get("iduser", Integer.class);
    }

    public String extractRole(String token) {
        return parseToken(token).getBody().get("rol", String.class);
    }

    // ==========================================================
    // VALIDACIÓN
    // ==========================================================
    public boolean isTokenValid(String token, String email) {
        try {
            return email.equals(extractEmail(token)) && !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isExpired(String token) {
        return parseToken(token).getBody().getExpiration().before(new Date());
    }

    // ==========================================================
    // PARSEAR TOKEN (Acepta Bearer + seguridad)
    // ==========================================================
    private Jws<Claims> parseToken(String token) {

        token = token.replace("Bearer ", "").trim();

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

        } catch (JwtException e) {
            throw new RuntimeException(" Token inválido o corrupto: " + e.getMessage());
        }
    }
}
