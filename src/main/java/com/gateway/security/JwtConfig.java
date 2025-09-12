package com.gateway.security;

import com.gateway.config.auth.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
class JwtConfig {

    private final JwtProperties jwtProperties;


    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        String jwkSetUri = jwtProperties.getJwkSetUri();
        if (jwkSetUri == null || jwkSetUri.isEmpty()) {
            throw new IllegalStateException("El valor de jwk-set-uri no está configurado correctamente.");
        }
        log.info("Using jwkSetUri={}", jwkSetUri);
        return NimbusReactiveJwtDecoder
                .withJwkSetUri(jwkSetUri)
                .build();
    }
}