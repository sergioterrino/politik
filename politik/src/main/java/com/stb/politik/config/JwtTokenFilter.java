// package com.stb.politik.config;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import java.io.IOException;

// public class JwtTokenFilter extends OncePerRequestFilter {

//     private JwtTokenProvider tokenProvider;

//     public JwtTokenFilter(JwtTokenProvider tokenProvider) {
//         this.tokenProvider = tokenProvider;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//         String token = tokenProvider.resolveToken(request);

//         if (token != null && tokenProvider.validateToken(token)) {
//             Authentication auth = tokenProvider.getAuthentication(token);
//             SecurityContextHolder.getContext().setAuthentication(auth);
//         }
//         filterChain.doFilter(request, response);
//     }


//     // @Override
//     // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//     //         throws ServletException, IOException {
//     //     // Aquí es donde debes implementar la lógica para extraer el token JWT de la solicitud,
//     //     // validar el token y establecer la autenticación para el contexto de seguridad de Spring.

//     //     // Por ejemplo:
//     //     String token = request.getHeader("Authorization");
//     //     if (token != null) {
//     //         // Validar el token...
//     //         // Si el token es válido, establecer la autenticación:
//     //         Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Deberías crear un objeto Authentication basado en el token
//     //         SecurityContextHolder.getContext().setAuthentication(auth);
//     //     }

//     //     filterChain.doFilter(request, response);
//     // }


// }