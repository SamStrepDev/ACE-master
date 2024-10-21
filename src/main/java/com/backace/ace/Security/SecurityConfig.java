package com.backace.ace.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (si no es necesario para tu caso actual)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura CORS
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login").permitAll() // Permite acceso sin autenticación a la API de autenticación
                        .requestMatchers("/solicitudes").permitAll()
                        .requestMatchers("/api/garantia/**").permitAll()
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra ruta
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Establece que no se mantendrá el estado de sesión

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:5500")); // Permite el origen de tu frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permite todos los encabezados
        configuration.setAllowCredentials(true); // Permite el envío de credenciales (como cookies)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica la configuración CORS a todas las rutas
        return source;
    }
}
