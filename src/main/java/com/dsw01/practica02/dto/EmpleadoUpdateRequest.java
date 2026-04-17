package com.dsw01.practica02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record EmpleadoUpdateRequest(
    @NotBlank(message = "nombre es obligatorio")
    @Size(max = 100, message = "nombre debe tener máximo 100 caracteres")
    String nombre,

    @NotBlank(message = "direccion es obligatoria")
    @Size(max = 100, message = "direccion debe tener máximo 100 caracteres")
    String direccion,

    @NotBlank(message = "telefono es obligatorio")
    @Size(max = 100, message = "telefono debe tener máximo 100 caracteres")
    String telefono,

    @Size(min = 3, max = 60, message = "username debe tener entre 3 y 60 caracteres")
    @Pattern(regexp = "^$|.*\\S.*", message = "username no puede ser solo espacios")
    String username,

    @Size(min = 8, max = 128, message = "password debe tener entre 8 y 128 caracteres")
    @Pattern(regexp = "^$|.*\\S.*", message = "password no puede ser solo espacios")
    String password,

    @NotNull(message = "departamentoId es obligatorio")
    @Positive(message = "departamentoId debe ser mayor que 0")
    Long departamentoId
) {
}
