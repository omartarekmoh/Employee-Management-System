package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

// Security configuration class for setting up JWT authentication, authorization rules, and password encoding.
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Filter for processing JWT authentication.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // Entry point for handling unauthorized access (401 errors).
    private final AuthEntryPointJwt authEntryPointJwt;
    // Handler for access denied (403 errors) due to insufficient roles.
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Configures the security filter chain, including endpoint permissions, exception handling, and JWT filter.
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authEntryPointJwt) // 401 handler for unauthorized users
                        .accessDeniedHandler(accessDeniedHandler)   //  403 handler for wrong roles
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees", "/api/departments").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employees", "/api/departments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**", "/api/departments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**", "/api/departments/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides a BCrypt password encoder bean with strength 12.
     * @return The password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

