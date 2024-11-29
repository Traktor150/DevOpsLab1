package com.kth.apigateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {
    @Value("${message.service.url}")
    private String messageServiceUrl;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> messageServiceRoute() {
        return GatewayRouterFunctions.route("messages_service")
                .route(RequestPredicates.path("/api/messages/**"), HandlerFunctions.http(messageServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {
        return GatewayRouterFunctions.route("auth_service")
                .route(RequestPredicates.path("/api/auth/**"), HandlerFunctions.http(authServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> defaultRoute() {
        return GatewayRouterFunctions.route("default_route")
                .route(RequestPredicates.all(), HandlerFunctions.http("http://localhost:8083"))
                .build();
    }
}
