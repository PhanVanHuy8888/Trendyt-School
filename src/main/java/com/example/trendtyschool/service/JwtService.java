package com.example.trendtyschool.service;

import com.example.trendtyschool.model.Entity.Account;
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
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;

@Service
public class JwtService {
    //    IdAccount, Role,
    @Value("${jwt.expiryHour}")
    private Long expiryHour;

    @Value("${jwt.expiryDay}")
    private Long expiryDay;

    @Value("${jwt.secretKey}")
    private String secretKey;


    public String generateToken(Account account) {
        return generateToken(new HashMap<>(), account);
    }


    public String generateRefreshToken(Account account) {
        return generateRefreshToken(new HashMap<>(), account);
    }

    // Lấy username từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // kiểm tra token hợp lệ hay ko
    public boolean isValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }


    private String generateToken(Map<String, Object> claims, Account account) {
        return Jwts.builder()
                .setClaims(claims)
                .claim("Scope", buildScope(account))
                .claim("accountId", account.getId())
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private String generateRefreshToken(Map<String, Object> claims, Account account) {
        return Jwts.builder()
                .setClaims(claims)
                .claim("Scope", buildScope(account))
                .claim("accountId", account.getId())
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // lấy secretKey để ký token
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // lấy 1 claim cụ thể
    public  <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    // lấy tất cả các claim
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    // Lấy ra role để thêm vào token
    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (account.getRole() != null) {
            stringJoiner.add("ROLE_" + account.getRole().getNameRole());
        }

        return stringJoiner.toString();
    }

    public Date expiryTime() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour);
    }
}
