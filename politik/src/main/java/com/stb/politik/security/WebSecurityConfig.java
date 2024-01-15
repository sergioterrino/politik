package com.stb.politik.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.stb.politik.user.UserController;
import com.stb.politik.user.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
// @EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class.getName());

    // @Autowired
    // private UserRepository userRepository;

    private final UserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        log.info("WebSecurityConfig - filterChain() - jwtAuthenticationFilter: " + jwtAuthenticationFilter.toString());
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/users/login");

        CorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        ((UrlBasedCorsConfigurationSource) corsConfigurationSource).registerCorsConfiguration("/**",
                new CorsConfiguration().applyPermitDefaultValues());

        return http
                .cors()
                .and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests()
                .requestMatchers("/login", "/users/**", "/posts/**", "/api/users/**", "/api/**")
                .permitAll() // Estas dos lineas as he comentado para que me exija estar autenticado
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Esto es para cuando comienzo a hacer la auth, pero despues de crear todo lo
    // de security, lo comento
    // @Bean
    // public UserDetailsService userDetailsService() {
    // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    // manager.createUser(User.withUsername("admin")
    // .password(passwordEncoder().encode("admin"))
    // .roles()
    // .build());
    // return manager;
    // }

    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        log.info("\"WebSecurityConfig - authManager-------------------------");
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    // ESte es el que me funciona al principio sin implementar la autenticacion
    // completa
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // // ...

    // CorsConfigurationSource corsConfigurationSource = new
    // UrlBasedCorsConfigurationSource();
    // ((UrlBasedCorsConfigurationSource)
    // corsConfigurationSource).registerCorsConfiguration("/**", new
    // CorsConfiguration().applyPermitDefaultValues());

    // http
    // .csrf(csrf -> csrf.disable())
    // .cors(cors -> cors.configurationSource(corsConfigurationSource))
    // .authorizeHttpRequests(authorize -> authorize
    // .requestMatchers("/users/**").permitAll()
    // .requestMatchers("/posts/**").permitAll()
    // .anyRequest().authenticated());
    // // .addFilterBefore(new JwtTokenFilter(tokenProvider),
    // // UsernamePasswordAuthenticationFilter.class); // Cualquier otra solicitud
    // requiere
    // // // autenticación
    // // // .and()
    // // // .addFilterBefore(new JwtTokenFilter(),
    // // // UsernamePasswordAuthenticationFilter.class));
    // return http.build();
    // }

    // Este es el que me funcionaba al principio sin implementar la autenticacion
    // completa
    // @Bean
    // public UserDetailsService userDetailsService() {
    // PasswordEncoder encoder = passwordEncoder();
    // return username -> {
    // // Aquí puedes buscar el usuario en tu base de datos por su nombre de usuario
    // // y devolver un objeto UserDetails con los detalles del usuario.
    // // Por ahora, solo devolveremos un usuario con nombre de usuario "user" y
    // // contraseña "password".
    // UserDetails user = User.withUsername("user")
    // .password(encoder.encode("password"))
    // .roles("USER")
    // .build();
    // return user;
    // };
    // }

    // // @Bean
    // // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    // {
    // // http
    // // .csrf(csrf -> csrf.disable())
    // // .cors(withDefaults())
    // // .authorizeHttpRequests(authorize -> authorize
    // // .requestMatchers("/users/**").permitAll()
    // // .requestMatchers("/posts/**").permitAll()
    // // .anyRequest().authenticated());
    // // // .addFilterBefore(new JwtTokenFilter(tokenProvider),
    // // // UsernamePasswordAuthenticationFilter.class); // Cualquier otra
    // solicitud requiere
    // // // // autenticación
    // // // // .and()
    // // // // .addFilterBefore(new JwtTokenFilter(),
    // // // // UsernamePasswordAuthenticationFilter.class));
    // // return http.build();
    // // }

}

// // @Bean
// // public UserDetailsService userDetailsService() {
// // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
// // userRepository.findAll().forEach(user -> {
// // manager.createUser(User.withUsername(user.getUsername())
// // .password(user.getPassword())
// // .roles("USER")
// // .build());
// // });
// // }