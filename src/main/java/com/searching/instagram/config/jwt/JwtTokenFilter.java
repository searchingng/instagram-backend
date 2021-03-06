package com.searching.instagram.config.jwt;

import com.searching.instagram.config.detail.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpHeaders;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String auth = (String) request.getHeader("Authorization");
        if (request.getRequestURI().startsWith("/swagger")
                || request.getRequestURI().startsWith("/webjars")
                || request.getRequestURI().startsWith("/csrf")
                || request.getRequestURI().startsWith("/v2/api-docs")
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        if (auth == null || auth.isBlank() || !auth.startsWith("Bearer ")) {
            response.setStatus(401);
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = auth.split(" ")[1].trim();
        JwtDTO jwtDto = JwtUtil.parseJwt(jwt);

        if (jwtDto == null) {
            response.setStatus(401);
            filterChain.doFilter(request, response);
            return;
        }

        String userName = jwtDto.getUsername();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");

        filterChain.doFilter(request, response);
    }
}
