package com.educonnect.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF, enable stateless session, and configure authorization
        http
                .csrf(Customizer -> Customizer.disable()) // Disable CSRF for APIs
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session for APIs
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/journal/**", "/user/**", "/public/**").permitAll() // Publicly accessible
                                                                                              // URLs
                        .anyRequest().authenticated() // All other URLs require authentication
                )
                .httpBasic(); // Use Basic Auth for APIs

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
