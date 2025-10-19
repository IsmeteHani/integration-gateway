package se.moln.integrationgateway.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // 1) Lejo UI-n tënd (pa slash fundor)
        cfg.setAllowedOrigins(List.of(
                "https://stigmfrontendisme01.z16.web.core.windows.net",
                "http://localhost:5173"
        ));
        // Nëse ende del 403, në vend të setAllowedOrigins përdor këtë:
        // cfg.setAllowedOriginPatterns(List.of("https://*.web.core.windows.net", "http://localhost:5173"));

        // 2) Vetëm metodat që përdor UI
        cfg.setAllowedMethods(List.of("GET", "OPTIONS"));

        // 3) Header-at
        cfg.setAllowedHeaders(List.of("*"));

        // 4) Pa cookie/sesion nga browser-i (më i sigurt)
        cfg.setAllowCredentials(false);

        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Zbato CORS vetëm për API:
        source.registerCorsConfiguration("/api/**", cfg);
        return source;
    }
}
