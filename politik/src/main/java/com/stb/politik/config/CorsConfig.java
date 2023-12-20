package com.stb.politik.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Permitir todos los orígenes, métodos y encabezados necesarios
        corsConfig.addAllowedOrigin("http://localhost:4200");
        corsConfig.addAllowedMethod("*"); //permite POST, PUT, GET, DELETE, etc
        corsConfig.addAllowedHeader("*");

        CorsConfigurationSource source = request -> corsConfig;

        return new CorsFilter(source);
    }
}