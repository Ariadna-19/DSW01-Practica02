package com.dsw01.practica02.exception;

public class DepartamentoInUseException extends RuntimeException {

    public DepartamentoInUseException(Long id) {
        super("No se puede eliminar el departamento con id " + id + " porque tiene empleados asociados.");
    }
}
