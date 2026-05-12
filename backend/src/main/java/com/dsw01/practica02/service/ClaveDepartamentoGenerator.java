package com.dsw01.practica02.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClaveDepartamentoGenerator {

    private final JdbcTemplate jdbcTemplate;

    public ClaveDepartamentoGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String nextClave() {
        Long nextValue = jdbcTemplate.queryForObject("SELECT nextval('departamento_clave_seq')", Long.class);
        if (nextValue == null) {
            throw new IllegalStateException("No fue posible generar la clave de departamento.");
        }
        return "D-" + String.format("%04d", nextValue);
    }
}