package com.example.altech.service;

import com.example.altech.constant.TokenType;
import com.example.altech.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Service.
 */
@Service
public class JwtService {

    @Value("${jwt.expiryHour}")
    private long expiryHour;

    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    public String generateToken(UserDTO user) {
        return generateToken(Map.of(), user);
    }

    private String generateToken(
            Map<String, Object> claims, UserDTO userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDTO user) {
        return generateRefreshToken(Map.of(), user);
    }

    private String generateRefreshToken(
            Map<String, Object> claims, UserDTO userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay))
                .signWith(getKey(TokenType.REFRESH_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token, TokenType type) {
        return extractClaim(token, type, Claims::getSubject);
    }

    public boolean isValid(String token, TokenType type, UserDetails user) {
        final String username = extractUsername(token, type);
        return username.equals(user.getUsername());
    }

    private Key getKey(TokenType type) {
        if (TokenType.ACCESS_TOKEN.equals(type)) {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        } else {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
        }
    }

    private <T> T extractClaim(
            String token, TokenType type, Function<Claims, T> claimResolver) {
        final Claims claims = extraAllClaim(token, type);
        return claimResolver.apply(claims);
    }

    private Claims extraAllClaim(String token, TokenType type) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(type)).build().parseClaimsJws(token).getBody();
    }
}
