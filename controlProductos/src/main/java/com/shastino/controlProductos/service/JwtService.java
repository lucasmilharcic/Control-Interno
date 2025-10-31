package com.shastino.controlProductos.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Ojo: la clave debe tener al menos 32 bytes para HS256
    private static final String SECRET_KEY = "12345678901234567890123456789012"; // 32 chars
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24)) // 24 horas
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el username devolviendo null si el token no es parseable o firma inválida.
     * Esto evita lanzar excepciones desde el filtro.
     */
    public String extractUsernameSafe(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            // token inválido / expirado / mal formado => devolver null
            return null;
        }
    }

    /**
     * Método usado por el filtro para validar:
     * - firma/estructura
     * - que no esté vencido
     * - y que el subject coincida con el username esperado
     */
    public boolean isTokenValid(String token, String username) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String tokenUser = claims.getSubject();
            Date expiration = claims.getExpiration();

            return (tokenUser != null && tokenUser.equals(username)) && (expiration != null && expiration.after(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
