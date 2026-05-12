package com.dsw01.practica02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartamentoCreateRequest(
    @NotBlank(message = "nombre es obligatorio")
    @Size(max = 100, message = "nombre debe tener máximo 100 caracteres")
    String nombre,

    @Size(max = 255, message = "descripcion debe tener máximo 255 caracteres")
    String descripcion
) {
}