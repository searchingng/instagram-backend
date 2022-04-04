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

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String auth = (String) request.getHeader("Authorization");

        if (auth == null || auth.isBlank() || !auth.startsWith("Bearer ")){
            response.setStatus(401);
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = auth.split(" ")[1].trim();
        JwtDTO jwtDto = JwtUtil.parseJwt(jwt);

        if (jwtDto == null){
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

        filterChain.doFilter(request, response);
    }
}
