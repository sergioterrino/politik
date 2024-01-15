// package com.stb.politik.config;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import jakarta.servlet.http.HttpServletRequest;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import com.stb.politik.user.CustomUserDetailsService;

// import java.util.Date;

// @Component
// public class JwtTokenProvider {

//     @Value("${jwt.secret}")
//     private String secretKey ; // Reemplaza esto con tu propia clave secreta

//     private final long validityInMilliseconds = 3600000; // 1h

//     @Autowired
//     private CustomUserDetailsService customUserDetailsService;

//     public String createToken(Authentication authentication) {

//         UserDetails userDetails = (UserDetails) authentication.getPrincipal();

//         Date now = new Date();
//         Date validity = new Date(now.getTime() + validityInMilliseconds);

//         return Jwts.builder()
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(now)
//                 .setExpiration(validity)
//                 .signWith(SignatureAlgorithm.HS256, secretKey)
//                 .compact();
//     }

//     public Authentication getAuthentication(String token) {
//         UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(getUsername(token));
//         return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//     }

//     public String getUsername(String token) {
//         return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//     }

//     public String resolveToken(HttpServletRequest req) {
//         String bearerToken = req.getHeader("Authorization");
//         if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//             return bearerToken.substring(7, bearerToken.length());
//         }
//         return null;
//     }

//     public boolean validateToken(String token) {
//         try {
//             Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }
// }