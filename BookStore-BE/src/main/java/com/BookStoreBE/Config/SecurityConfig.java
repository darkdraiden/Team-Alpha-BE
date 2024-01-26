package com.BookStoreBE.Config;

import com.BookStoreBE.Security.JwtAuthenticationEntryPoint;
import com.BookStoreBE.Security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/user/login").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/user/signup").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/book").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/**").authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
