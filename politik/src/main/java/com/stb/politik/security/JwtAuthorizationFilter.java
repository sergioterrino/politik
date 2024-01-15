package com.stb.politik.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//esta clase será para la 2nda parte de la autenticación
//para cuando el user está utilizando el token para intentar acceder a un endpoint, no para autenticarse

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        log.info("JwtAuthorizationFilter.doFilterInternal() - before - bearerTOken ->" + bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.replace("Bearer ", "");
            log.info("JwtAuthorizationFilter.doFilterInternal() - after - jwt ->" + jwt);

            UsernamePasswordAuthenticationToken usernamePAT = JwtUtils.getAuthentication(jwt);
            log.info("JwtAuthorizationFilter.doFilterInternal() - after - usernamePAT ->" + usernamePAT.toString());
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
        }
        filterChain.doFilter(request, response);
    }

}