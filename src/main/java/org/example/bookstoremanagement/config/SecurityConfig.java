package org.example.bookstoremanagement.config;

import lombok.RequiredArgsConstructor;
import org.example.bookstoremanagement.config.FilterConfig;
import org.example.bookstoremanagement.security.JwtTokenProvider;
import org.example.bookstoremanagement.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final FilterConfig filterConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Everyone can register/login
                        .requestMatchers("/api/auth/**").permitAll()

                        // Everyone can GET books
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()

                        // All other requests (POST, PUT, DELETE, etc.) to /api/books/** require admin
                        .requestMatchers("/api/books/**").hasRole("ADMIN")
                        .requestMatchers("/api/authors/**").hasRole("ADMIN")
                        .requestMatchers("api/genres/**").hasRole("ADMIN")
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                // If purely JWT-based, disable form login
//                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form.disable())

                // Insert JWT filter
                .addFilterBefore(
                        filterConfig.jwtAuthenticationFilter(jwtTokenProvider, jpaUserDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}
