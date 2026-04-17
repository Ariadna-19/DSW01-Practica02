package com.dsw01.practica02.config;

import com.dsw01.practica02.repository.AuthJdbcRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator/health").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/empleados/**").authenticated()
                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
        @Qualifier("customUserDetailsService") UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("customUserDetailsService")
    public UserDetailsService userDetailsService(
        @Value("${spring.security.user.name:admin}") String username,
        @Value("${spring.security.user.password:admin123}") String password,
        @Value("${spring.security.user.roles:USER}") String role,
        PasswordEncoder passwordEncoder,
        AuthJdbcRepository authJdbcRepository
    ) {
        final String normalizedAdminUsername = username == null ? "admin" : username.trim();
        final String adminPassword = (password == null || password.isBlank()) ? "admin123" : password.trim();
        final UserDetails adminUser = User.withUsername(username)
            .password(passwordEncoder.encode(adminPassword))
            .roles(role)
            .build();

        return requestedUsername -> {
            String normalizedRequestedUsername = requestedUsername == null ? "" : requestedUsername.trim();

            if (normalizedRequestedUsername.equalsIgnoreCase(normalizedAdminUsername)
                || normalizedRequestedUsername.equalsIgnoreCase("admin")) {
                return adminUser;
            }

            return authJdbcRepository.findByEmail(normalizedRequestedUsername)
                .map(credential -> User.withUsername(credential.username())
                    .password(toBcryptPassword(credential.passwordHash()))
                    .roles("USER")
                    .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        };
    }

    private String toBcryptPassword(String storedPassword) {
        if (storedPassword == null || storedPassword.isBlank()) {
            return "$2a$10$7EqJtq98hPqEX7fNZaFWoOHiYx2R8.6vWf4Yj9V7jJvK5OQx6m4m2";
        }

        String trimmedPassword = storedPassword.trim();

        if (trimmedPassword.startsWith("{bcrypt}")) {
            return trimmedPassword.substring("{bcrypt}".length());
        }

        return trimmedPassword;
    }
}
