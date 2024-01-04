package com.stb.politik.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.stb.politik.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        // Genera una clave segura para HS256.
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Genera el token JWT.
        String jwt = Jwts.builder()
            .setSubject(user.getUsername())
            // ... puedes añadir más reclamaciones aquí si lo necesitas
            .signWith(key)
            .compact();

        return jwt;
    }
}
