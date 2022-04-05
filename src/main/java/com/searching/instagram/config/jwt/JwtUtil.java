package com.searching.instagram.config.jwt;

import com.searching.instagram.entity.enums.Role;
import com.searching.instagram.exceptions.ForbiddenException;
import com.searching.instagram.exceptions.UnauthorizedException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private final static String key = "SearchingKey";

    public static String generateJwt(Long id, String username) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + 1000 * 60 * 60);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuer("Topaloq")
                .claim("userName", username);

        return jwtBuilder.compact();
    }

    public static String generateJwt(Long id) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + 1000 * 60 * 10);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuer("Topaloq");

        return jwtBuilder.compact();
    }

    public static JwtDTO parseJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();

            Long id = Long.valueOf(claims.getSubject());
            String username = (String) claims.get("userName");
            Role role = Role.valueOf((String) claims.get("role"));


            return new JwtDTO(id, username, role);
        } catch (JwtException e) {
            return null;
        }
    }

    public static Long parseLongJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();

        Long id = Long.valueOf(claims.getSubject());

        return id;
    }

    public static JwtDTO getProfile(HttpServletRequest request, Role requiredRole) {
        JwtDTO jwtDTO = (JwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new UnauthorizedException("Not authorized");
        }

        if (!jwtDTO.getRole().equals(requiredRole)) {
            throw new ForbiddenException("Forbidden exp");
        }
        return jwtDTO;
    }

    /*public static JwtDTO getCurrentUser(HttpServletRequest request){

        JwtDTO jwtDTO = (JwtDTO) request.getAttribute("jwtDto");

        if (jwtDTO == null)
            throw new RuntimeException("Not Authorized (JwtDto is Null)");

        return jwtDTO;
    }*/

    /*public static JwtDTO getCurrentUser(HttpServletRequest request, UserRole... roles){

        JwtDTO jwtDTO = (JwtDTO) request.getAttribute("jwtDto");

        if (jwtDTO == null)
            throw new UnauthorizedException("Not Authorized (JwtDto is Null)");

        if (!List.of(roles).contains(jwtDTO.getRole())){
            throw new ForbiddenException("This is Forbidden for you");
        }

        return jwtDTO;
    }*/

}
