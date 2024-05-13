package com.santi.backend.usersApp.backendusersApp.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santi.backend.usersApp.backendusersApp.models.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.santi.backend.usersApp.backendusersApp.auth.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //attemptAuthentication toma los datos del login (ya tiene implementada la ruta /login)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();

//            logger.info("Username from InputStream request (raw) " + username);
//            logger.info("Password from InputStream request (raw) " + password); INSEGURO PARA PRODUCCIÓN

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal())
                .getUsername();
        String originalInput = SECRET_KEY + "." + username;
        String token = Base64.getEncoder().encodeToString(originalInput.getBytes());

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("Hello %s, You've logged in successfully!", username));
        body.put("username", username);
        response.getWriter()
                .write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("appplication/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
         Map<String, Object> body = new HashMap<>();
         body.put("message", "Authentication error. Wrong username or password!");
         body.put("error", failed.getMessage());

         response.getWriter().write(new ObjectMapper().writeValueAsString(body));
         response.setStatus(401);
         response.setContentType("application/json");
    }
}
