package com.gateway.beans;

import com.gateway.config.ServiceConfig;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.Duration;

@Configuration
@Profile("!prod")
public class GatewayConfig {
    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msvc-chat-websocket",route-> route
                        .path("/ws/chat/**")
                        .uri("lb:ws://msvc-chat"))
                .route("msvc-billing", route -> route
                        .path("/billing/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("billingCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/billing")
                                )
                        )
                        .uri("lb://msvc-billing"))
                .route("msvc-chat", route -> route
                        .path("/chat/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("chatCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/chat")
                                )
                        )
                        .uri("lb://msvc-chat"))
                .route("msvc-classes", route -> route
                        .path("/classes/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("classesCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/classes")
                                )
                        )
                        .uri("lb://msvc-classes"))
                .route("msvc-members", route -> route
                        .path("/members/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("membersCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/members")
                                )
                        )
                        .uri("lb://msvc-members"))
                .route("msvc-notifications", route -> route
                        .path("/notifications/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("notificationsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/notifications")
                                )
                        )
                        .uri("lb://msvc-notifications"))
                .route("msvc-security", route -> route
                        .path("/security/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("securityCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/security")
                                )
                        )
                        .uri("lb://msvc-security"))
                .build();
    }
}

@Configuration
@Profile("prod")
class AzureGatewayConfig {
    private final ServiceConfig serviceConfig;

    public AzureGatewayConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msvc-billing", route -> route
                        .path("/billing/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("billingCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/billing")
                                )
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT)
                                        .setMethods(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.HEAD)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(5), 2, true)
                                )
                        )
                        .uri(serviceConfig.getBilling()))
                .route("msvc-chat", route -> route
                        .path("/chat/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("chatCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/chat")
                                ))
                        .uri(serviceConfig.getChat()))
                .route("msvc-classes", route -> route
                        .path("/classes/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("classesCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/classes")
                                )
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT)
                                        .setMethods(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.HEAD)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(5), 2, true)
                                )
                        )
                        .uri(serviceConfig.getClasses()))
                .route("msvc-members", route -> route
                        .path("/members/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("membersCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/members")
                                )
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT)
                                        .setMethods(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.HEAD)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(5), 2, true)
                                )
                        )
                        .uri(serviceConfig.getMembers()))
                .route("msvc-notifications", route -> route
                        .path("/notifications/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("notificationsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/notifications")
                                )
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT)
                                        .setMethods(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.HEAD)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(5), 2, true)
                                )
                        )
                        .uri(serviceConfig.getNotifications()))
                .route("msvc-security", route -> route
                        .path("/security/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("securityCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/security")
                                )
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT)
                                        .setMethods(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.HEAD)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(5), 2, true)
                                )
                        )
                        .uri(serviceConfig.getSecurity()))
                .build();
    }

//    private Function<Route, RouteLocatorBuilder.Builder.Buildable> createServiceRoute(
//            String serviceName, String uri, boolean enableRetry) {
//
//        return route -> {
//            RouteLocatorBuilder.Builder.UriSpec uriSpec = route
//                    .path("/" + serviceName + "/**")
//                    .filters(f -> {
//                        GatewayFilterSpec filter = f.stripPrefix(1)
//                                .stripPrefix(1)
//                                .circuitBreaker(config -> config
//                                        .setName(serviceName + "CircuitBreaker")
//                                        .setFallbackUri("forward:/fallback/" + serviceName)
//                                );
//
//                        if (enableRetry) {
//                            filter.retry(retryConfig -> retryConfig
//                                    .setRetries(3)
//                                    .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT)
//                                    .setMethods(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.HEAD)
//                                    .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(5), 2, true));
//                        }
//
//                        return filter;
//                    });
//
//            return uriSpec.uri(uri);
//        };
//    }
//
//    private Function<RouteLocatorBuilder.Builder.Route, RouteLocatorBuilder.Builder.Buildable> createServiceRoute(
//            String serviceName, String uri) {
//        return createServiceRoute(serviceName, uri, true);
//    }
}
