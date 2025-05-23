package com.example.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
public class SecurityConfig {

    /*@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> {
                    auth.pathMatchers("/actuator/**").permitAll();
                    auth.anyExchange().authenticated();
                })
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
*/

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> {
                    auth.pathMatchers("/actuator/**").permitAll();
                    auth.anyExchange().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());


        return http.build();
    }
}