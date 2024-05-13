package com.santi.backend.usersApp.backendusersApp.auth;

import com.santi.backend.usersApp.backendusersApp.auth.filters.JwtAuthenticationFilter;
import com.santi.backend.usersApp.backendusersApp.auth.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> authz
        .requestMatchers(HttpMethod.GET, "/users").permitAll()
        .anyRequest().authenticated())
        .addFilter(new JwtAuthenticationFilter(this.authenticationManager()))
        .addFilter(new JwtValidationFilter(this.authenticationManager()))
        .csrf(AbstractHttpConfigurer::disable)//se desactiva CSRF en API Rest (sirve sólo para server-side rendering)
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //no maneja estados porque se usa el JWT
        .build();
    }
}
