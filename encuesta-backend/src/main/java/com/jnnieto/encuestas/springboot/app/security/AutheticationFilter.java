package com.jnnieto.encuestas.springboot.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnnieto.encuestas.springboot.app.models.requests.UserLoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AutheticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AutheticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Se mapean los datos que vienen de la request a la clase UserLoginRequestModel
            UserLoginRequestModel usermodel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
            // Se autentica el usuario que viene de la request usando el email y el password
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usermodel.getEmail(), usermodel.getPassword(), new ArrayList<>()));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Método que se ejecuta cuando la autenticación es válida
     * Aquí se genera el token bansándose en los roles y correos
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = ((User) authResult.getPrincipal()).getUsername();

        // Aquí se genera el token
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();

        String data = new ObjectMapper().writeValueAsString(Map.of("token", SecurityConstants.TOKEN_PREFIX + token));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(data);
        response.flushBuffer();
    }
}
