package com.stb.politik.security;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stb.politik.credentials.Credentials;
import com.stb.politik.user.UserController;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class.getName());

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.info("JwtAuthenticationFilter.attemptAuthentication()------------------------------------");
        AuthCredentials authCredentials = new AuthCredentials();
        log.info("Request: " + request.toString());
        try {
            log.info("Request: ----------try " + request.getReader().toString());
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
            log.info("AuthCredentials: " + authCredentials.toString());
        } catch (IOException e) {
            log.info("Request: ----------catch " + e.getMessage());
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getUsername(), authCredentials.getPassword(), Collections.emptyList());

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        log.info("JwtAuthenticationFilter.successfulAuthentication()-----------------------------------------------");
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String jwt = JwtUtils.generateToken(userDetails.getUsername());

        response.addHeader("Authorization", "Bearer " + jwt);
        response.getWriter().flush();

        log.info("jwt: " + jwt);
        log.info("Response: " + response.toString());

        super.successfulAuthentication(request, response, chain, authResult);
    }

}
