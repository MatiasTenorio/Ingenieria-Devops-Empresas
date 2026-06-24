package com.M3Tours.empresas.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.M3Tours.empresas.Security.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .  csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
        // Endpoint libre para pedir el token sin credenciales
            .requestMatchers("/auth/login", "/actuator/prometheus").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(
            new JwtAuthorizationFilter(),
            UsernamePasswordAuthenticationFilter.class)
        .build();
    }
}
