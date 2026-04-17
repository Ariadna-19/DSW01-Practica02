package com.dsw01.practica02.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuthJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<AuthCredential> findByEmail(String email) {
        String sql = """
            SELECT clave, username, password_hash
            FROM empleados
            WHERE lower(email) = lower(?)
            LIMIT 1
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new AuthCredential(
                rs.getString("clave"),
                rs.getString("username"),
                rs.getString("password_hash")
            ), email)
            .stream()
            .findFirst();
    }

    public record AuthCredential(String clave, String username, String passwordHash) {
    }
}
