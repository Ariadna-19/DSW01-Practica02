package com.dsw01.practica02.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClaveEmpleadoGenerator {

    private final JdbcTemplate jdbcTemplate;

    public ClaveEmpleadoGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String nextClave() {
        Long nextValue = jdbcTemplate.queryForObject("SELECT nextval('empleado_clave_seq')", Long.class);
        if (nextValue == null) {
            throw new IllegalStateException("No fue posible generar la clave de empleado.");
        }
        return "E-" + String.format("%04d", nextValue);
    }
}
