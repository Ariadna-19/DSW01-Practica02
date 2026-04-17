package com.dsw01.practica02.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "email es obligatorio")
    @Email(message = "email debe tener formato válido")
    String email,

    @NotBlank(message = "password es obligatorio")
    String password
) {
}
