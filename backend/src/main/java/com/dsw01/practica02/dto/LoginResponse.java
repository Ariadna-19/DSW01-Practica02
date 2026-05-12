package com.dsw01.practica02.dto;

public record LoginResponse(
    boolean authenticated,
    String empleadoClave,
    String message
) {
}
