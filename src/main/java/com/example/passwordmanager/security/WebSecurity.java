package com.example.passwordmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                .csrf().disable()
                .headers((headers) -> headers
                        .frameOptions().disable())
                .authorizeHttpRequests((requests) -> requests
                   //     .requestMatchers("/api/v1/users/all").hasRole("ADMIN")
                     //   .requestMatchers("/api/v1/passwords/all").hasRole("ADMIN")
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .httpBasic();
        return httpSecurity.build();
    }
}
