package com.example.ms_orders.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /*@Bean
    @LoadBalanced
    public WebClient webClient(WebClient.Builder builder) {
        //return builder.baseUrl("http://localhost:8084/api/inventory")
        return builder.baseUrl("lb://ms-inventory/api/inventory")
                .build();
    }*/


    /*@Bean
    @LoadBalanced
    public WebClient client(){
        return WebClient.builder().baseUrl("lb://ms-inventory/api/inventory").build();
    }*/

    /*@Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }*/

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder().filter(new ServletBearerExchangeFilterFunction());
    }
}

