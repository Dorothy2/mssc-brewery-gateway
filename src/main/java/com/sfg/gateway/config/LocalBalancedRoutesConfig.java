package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("local-discovery")
@Configuration
public class LocalBalancedRoutesConfig {

	// annotate as bean so it gets brought into Spring Context
	@Bean
	public RouteLocator localBalancedRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(r -> r.path("/api/v1/beer*", "/api/v1/beer/*", "/api/v1/beer/upc/*")
					.uri("lb://beer-service")
					.id("beer-service"))
			.route(r -> r.path("/api/v1/customers/**")
					.uri("lb://order-service")
					.id("order-service"))
			.route(r ->r.path("/api/v1/beer/*/inventory")
					.uri("lb://inventory-service")
					.id("inventory-service"))	
			.route(r ->r.path("/inventory-failover")
					.uri("lb://inventory-failover")
					.id("inventory-failover"))	
			.build();
			
	}
}
