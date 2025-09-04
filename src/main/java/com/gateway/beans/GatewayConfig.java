package com.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod")
public class GatewayConfig {
    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msvc-billing-route", route -> route
                        .path("/billing/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://msvc-billing"))
                .route("msvc-chat", route -> route
                        .path("/chat/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://msvc-chat"))
                .route("msvc-classes", route -> route
                        .path("/classes/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://msvc-classes"))
                .route("msvc-members", route -> route
                        .path("/members/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://msvc-members"))
                .route("msvc-notifications", route -> route
                        .path("/notifications/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://msvc-notifications"))
                .route("msvc-security", route -> route
                        .path("/security/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://msvc-security"))
                .build();
    }
}

@Configuration
@Profile("prod")
class AzureGatewayConfig {
    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msvc-billing-route", route -> route
                        .path("/billing/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("https://msvc-billing-agathvhsh4f5c5a6.chilecentral-01.azurewebsites.net"))
                .route("msvc-chat", route -> route
                        .path("/chat/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://msvc-chat:9096"))
                .route("msvc-classes", route -> route
                        .path("/classes/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://msvc-classes:9094"))
                .route("msvc-members", route -> route
                        .path("/members/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://msvc-members:9092"))
                .route("msvc-notifications", route -> route
                        .path("/notifications/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://msvc-notifications:9095"))
                .route("msvc-security", route -> route
                        .path("/security/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://msvc-security:9091"))
                .build();
    }
}