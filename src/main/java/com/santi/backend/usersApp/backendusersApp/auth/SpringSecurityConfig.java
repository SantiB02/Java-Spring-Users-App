package com.santi.backend.usersApp.backendusersApp.auth;

import com.santi.backend.usersApp.backendusersApp.auth.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> authz
        .requestMatchers(HttpMethod.GET, "/users").permitAll()
        .anyRequest().authenticated())
        .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
        .csrf(AbstractHttpConfigurer::disable)//se desactiva CSRF en API Rest (sirve sólo para server-side rendering)
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //no maneja estados porque se usa el JWT
        .build();
    }
}
