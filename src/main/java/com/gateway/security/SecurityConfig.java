package com.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Bean
        @Order(2)
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
                return http
                                /**
                                 * Configuracion programatica de CORS
                                 */
                                // .cors(cors -> cors.configurationSource(exchange -> {
                                // CorsConfiguration config = new CorsConfiguration();
                                // config.setAllowCredentials(true);
                                // config.setAllowedOriginPatterns(List.of(
                                // "http://localhost:5173",
                                // "https://fit-desk.netlify.app"));
                                // config.setAllowedMethods(
                                // List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                                // config.addAllowedHeader("*");
                                // config.setMaxAge(3600L);
                                // return config;
                                // }))
                                .authorizeExchange(exchanges -> exchanges
                                                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                                                .pathMatchers(
                                                                "/actuator/**",
                                                                "/",
                                                                "/security/auth/login",
                                                                "/security/auth/status",
                                                                "/security/auth/refresh",
                                                                "/security/auth/register",
                                                                "/security/oauth2/**",
                                                                "/security/login/oauth2/**",
                                                                "/security/test/**",
                                                                "/auth/logout",
                                                                "/chat/test/**",
                                                                "/billing/payments/**",
                                                                "/classes/test/**",
                                                                "/fallback/**")
                                                .permitAll()
                                                .anyExchange().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                                .csrf(ServerHttpSecurity.CsrfSpec::disable).build();
        }

}