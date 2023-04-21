package com.stackroute.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean
    public RouteLocator getRoute(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p->p.path("/admin/**")
                        .uri("http://localhost:4400/"))
                .route(p->p.path("/interviewer/**")
                        .uri("http://localhost:4400/"))
                .route(p->p.path("/interviewee/**")
                        .uri("http://localhost:4400/"))
                .route(p->p.path("/questions/**")
                        .uri("http://localhost:4400/"))
                .route(p->p.path("/freeze/**")
                        .uri("http://localhost:4400/"))
                .route(p->p.path("/login/**")
                        .uri("http://localhost:4400/"))
                .build();
    }
}
