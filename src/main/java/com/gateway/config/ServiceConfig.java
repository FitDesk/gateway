package com.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "services.urls")
public class ServiceConfig {
    private String billing;
    private String chat;
    private String classes;
    private String members;
    private String notifications;
    private String security;

}