package com.stb.politik.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ssl.SslBundleProperties.Key;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Collections;

@Service
public class JwtUtils {

    private static Logger log = LoggerFactory.getLogger(JwtUtils.class.getName());

    // @Value("${jwt.secret}")
    // private static String secret;
    private static String secret = "UnDiaViUnaVacaVestidaDeUniforme"; // la sustituyo por una generada por ahora
    // Genera una clave segura para HS256.
    private static SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.expiration}")
    private static long timeToExpire;

    // Esto genera un token con el username del usuario que se enviará al cliente
    public static String generateToken(String username) {
        log.info("JwtUtils.generateToken() ->" + username);
        // pasamos a ms el timeToExpire
        long expirationTime = timeToExpire * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("username", username);

        // Genera el token JWT.
        return Jwts.builder()
                .setSubject(username)
                // .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(secretKey)
                .compact();
    }

    // para que Spring Security reconozca el token al intentar acceder a un endpoint
    public static UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
        log.info("JwtUtils.getAuthentication() ->" + jwt);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

        } catch (JwtException e) {
            return null;
        }
    }

}

// ESta es la 1ra que hice la más antigua
// public String generateToken(User user) {
// // Genera una clave segura para HS256.
// SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

// // Genera el token JWT.
// String jwt = Jwts.builder()
// .setSubject(user.getUsername())
// // ... puedes añadir más reclamaciones aquí si lo necesitas
// .signWith(key)
// .compact();

// return jwt;
// }