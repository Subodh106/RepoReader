package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource(String allowedOrigin){
        CorsConfiguration config = new CorsConfiguration();
        List<String> origin = Arrays.stream(allowedOrigin.split(","))
                .map(String::trim)
                .filter(String::isEmpty)
                .toList();

        config.setAllowedHeaders(List.of("*"));
        config.setAllowedOrigins(origin);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        config.setMaxAge(3600L);
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
