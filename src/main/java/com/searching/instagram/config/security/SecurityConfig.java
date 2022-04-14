package com.searching.instagram.config.security;

import com.searching.instagram.config.detail.CustomUserDetailsService;
import com.searching.instagram.config.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                //profile
                .antMatchers("/profile").hasAuthority("ADMIN_ROLE")
                .antMatchers("/profile/delete/**").hasAuthority("ADMIN_ROLE")
                .antMatchers("/profile/**").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                //attach
                .antMatchers("/attach/upload/**", "/attach/get/**").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                .antMatchers("/attach/open/**", "/attach/avatar/**").permitAll()
                .antMatchers("/attach/download/**").permitAll()
                //post
                .antMatchers(HttpMethod.POST, "/post").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                .antMatchers(HttpMethod.DELETE, "/post/**").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                .antMatchers("/post/**").permitAll()
                //comment
                .antMatchers(HttpMethod.POST, "/comment").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                .antMatchers(HttpMethod.DELETE, "/comment/**").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                .antMatchers("/comment/**").permitAll()
                //following
                .antMatchers(HttpMethod.POST, "/following").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                .antMatchers("/following/**").permitAll()
                //like
                .antMatchers("/like/**").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                //look (view)
                .antMatchers("/look/**").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")
                //save (saved posts)
                .antMatchers("/save/**").hasAnyAuthority("ADMIN_ROLE", "USER_ROLE")

                //admin
                .antMatchers("/admin/**").hasAuthority("ADMIN_ROLE")
                //swagger
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**");
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5Hex(String.valueOf(rawPassword));
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return DigestUtils.md5Hex(String.valueOf(rawPassword))
                        .equals(encodedPassword);
            }
        };
    }

  /*  @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
}
