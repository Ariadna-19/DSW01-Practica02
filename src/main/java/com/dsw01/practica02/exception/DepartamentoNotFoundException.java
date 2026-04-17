package com.dsw01.practica02.exception;

public class DepartamentoNotFoundException extends RuntimeException {

    public DepartamentoNotFoundException(Long id) {
        super("No existe un departamento con id: " + id);
    }
}