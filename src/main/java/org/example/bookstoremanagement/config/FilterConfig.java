package org.example.bookstoremanagement.config;

import org.example.bookstoremanagement.security.JwtAuthenticationFilter;
import org.example.bookstoremanagement.security.JwtTokenProvider;
import org.example.bookstoremanagement.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * Creates a JwtAuthenticationFilter bean with the matching constructor.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            JpaUserDetailsService jpaUserDetailsService
    ) {
        // Calls the constructor that takes (jwtTokenProvider, jpaUserDetailsService)
        return new JwtAuthenticationFilter(jwtTokenProvider, jpaUserDetailsService);
    }
}
