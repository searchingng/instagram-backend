package com.searching.instagram.config.jwt;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {
    private final static String key = "SearchingKey";

    public static String generateJwt(Long id, String username){

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + 1000 * 60 * 60);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuer("Instagram")
                .claim("userName", username);

        return jwtBuilder.compact();
    }

    public static String generateJwt(Long id){

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + 1000 * 60 * 10);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuer("Instagram");

        return jwtBuilder.compact();
    }


    public static JwtDTO parseJwt(String jwt){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();

            Long id = Long.valueOf(claims.getSubject());
            String username = (String) claims.get("userName");
            return new JwtDTO(id, username);
        } catch (JwtException e){

            return null;
        }
    }

    public static Long parseLongJwt(String jwt){

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();

        Long id = Long.valueOf(claims.getSubject());

        return id;
    }


}
