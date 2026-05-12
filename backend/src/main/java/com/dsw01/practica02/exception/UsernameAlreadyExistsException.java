package com.dsw01.practica02.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Ya existe un empleado con username: " + username);
    }
}
