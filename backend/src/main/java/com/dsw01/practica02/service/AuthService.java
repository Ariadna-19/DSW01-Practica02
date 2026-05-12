package com.dsw01.practica02.service;

import com.dsw01.practica02.dto.LoginResponse;
import com.dsw01.practica02.repository.AuthJdbcRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String INVALID_MESSAGE = "Credenciales inválidas";

    private final AuthJdbcRepository authJdbcRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthJdbcRepository authJdbcRepository, PasswordEncoder passwordEncoder) {
        this.authJdbcRepository = authJdbcRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return new LoginResponse(false, null, INVALID_MESSAGE);
        }

        return authJdbcRepository.findByEmail(email)
            .map(credential -> {
                boolean valid = passwordEncoder.matches(password, credential.passwordHash());
                if (!valid) {
                    return new LoginResponse(false, null, INVALID_MESSAGE);
                }
                return new LoginResponse(true, credential.clave(), "Autenticación exitosa");
            })
            .orElseGet(() -> new LoginResponse(false, null, INVALID_MESSAGE));
    }
}
