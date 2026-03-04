package com.dsw01.practica02.exception;

public class EmpleadoNotFoundException extends RuntimeException {

    public EmpleadoNotFoundException(String clave) {
        super("No existe un empleado con clave: " + clave);
    }
}
