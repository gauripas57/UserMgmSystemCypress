package com.usermanagement.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TokenValidator {

    private static final long EXPIRATION_TIME = 3600000; // 1 hour
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

      //  SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    /*
    public static boolean validateToken(String token) {
        try {
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
           System.out.println(claims);
            // Additional validation logic if needed
            // For example, check if the token subject (username) exists or if any specific claims are required

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return false;
        }
    }

}
