package com.dsw01.practica02.controller;

import com.dsw01.practica02.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.dto.DepartamentoPageResponse;
import com.dsw01.practica02.dto.DepartamentoResponse;
import com.dsw01.practica02.dto.DepartamentoUpdateRequest;
import com.dsw01.practica02.service.DepartamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartamentoResponse crear(@Valid @RequestBody DepartamentoCreateRequest request) {
        return departamentoService.crear(request);
    }

    @GetMapping("/{id}")
    public DepartamentoResponse obtenerPorId(
        @PathVariable @Min(1) Long id
    ) {
        return departamentoService.obtenerPorId(id);
    }

    @GetMapping
    public DepartamentoPageResponse listar(@RequestParam(defaultValue = "0") @Min(0) int page) {
        return departamentoService.listar(page);
    }

    @PutMapping("/{id}")
    public DepartamentoResponse actualizar(
        @PathVariable @Min(1) Long id,
        @Valid @RequestBody DepartamentoUpdateRequest request
    ) {
        return departamentoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Min(1) Long id) {
        departamentoService.eliminar(id);
    }
}