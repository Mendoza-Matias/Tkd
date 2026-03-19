package com.mmendoza.tkd.infrastructure.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mmendoza.tkd.core.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.refresh-expiration}")
    private String refreshExpiration;

    public String generateToken(UserDetails user) {
        return buildToken(generateExtraClaims(user, "access"), user.getUsername(), expiration);
    }

    public String generateRefreshToken(UserDetails user) {
        return buildToken(generateExtraClaims(user, "refresh"), user.getUsername(), refreshExpiration);
    }

    private String buildToken(Map<String, Object> claims, String subject, String expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(getSingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> generateExtraClaims(UserDetails user, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("roles", user.getAuthorities());
        claims.put("type", type);
        return claims;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token)
                && !isRefreshToken(token);
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token)
                && isRefreshToken(token);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Key getSingKey() {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String extractType(String token) {
        return extractAllClaims(token).get("type", String.class);
    }

    public boolean isRefreshToken(String token) {
        return extractType(token).equals("refresh");
    }
}
