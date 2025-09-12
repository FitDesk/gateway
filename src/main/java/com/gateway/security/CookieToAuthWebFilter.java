package com.gateway.security;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(-101)
public class CookieToAuthWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.equals("/security/auth/login") ||
                path.equals("/security/auth/status") ||
                path.equals("/security/auth/refresh") ||
                path.startsWith("/actuator/") ||
                path.startsWith("/fallback/") ||
                path.equals("/")) {
            return chain.filter(exchange);
        }

        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        HttpCookie cookie = cookies.getFirst("access_token");
        if (cookie == null) {
            return chain.filter(exchange);
        }

        String token = cookie.getValue();
        String authHeader = token.startsWith("Bearer ") ? token : "Bearer " + token;

        ServerWebExchange mutated = exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .build())
                .build();

        return chain.filter(mutated);
    }
}