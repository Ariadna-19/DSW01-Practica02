package com.dsw01.practica02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Practica02Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Practica02Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Practica02Application.class, args);
    }

    @Bean
    CommandLineRunner ejemploSimpleCrudDepartamentos() {
        return args -> LOGGER.info(
            "Ejemplo CRUD Departamentos: POST /api/departamentos {nombre, descripcion} y POST /api/empleados {nombre, direccion, telefono, departamentoId}."
        );
    }
}