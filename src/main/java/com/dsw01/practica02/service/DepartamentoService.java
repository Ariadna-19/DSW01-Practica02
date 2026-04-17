package com.dsw01.practica02.service;

import com.dsw01.practica02.domain.Departamento;
import com.dsw01.practica02.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.dto.DepartamentoPageResponse;
import com.dsw01.practica02.dto.DepartamentoResponse;
import com.dsw01.practica02.dto.DepartamentoUpdateRequest;
import com.dsw01.practica02.exception.DepartamentoInUseException;
import com.dsw01.practica02.exception.DepartamentoNotFoundException;
import com.dsw01.practica02.repository.DepartamentoDAO;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService {

    private static final int PAGE_SIZE = 5;

    private final DepartamentoDAO departamentoDAO;
    private final EmpleadoRepository empleadoRepository;

    public DepartamentoService(
        DepartamentoDAO departamentoDAO,
        EmpleadoRepository empleadoRepository
    ) {
        this.departamentoDAO = departamentoDAO;
        this.empleadoRepository = empleadoRepository;
    }

    public DepartamentoResponse crear(DepartamentoCreateRequest request) {
        Departamento departamento = new Departamento();
        departamento.setNombre(request.nombre());
        departamento.setDescripcion(request.descripcion());

        Departamento saved = departamentoDAO.save(departamento);
        return toResponse(saved);
    }

    public DepartamentoResponse obtenerPorId(Long id) {
        Departamento departamento = departamentoDAO.findById(id)
            .orElseThrow(() -> new DepartamentoNotFoundException(id));
        return toResponse(departamento);
    }

    public DepartamentoPageResponse listar(int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by("id").ascending());
        Page<Departamento> departamentoPage = departamentoDAO.findAll(pageRequest);
        return new DepartamentoPageResponse(
            departamentoPage.getContent().stream().map(this::toResponse).toList(),
            departamentoPage.getNumber(),
            departamentoPage.getSize(),
            departamentoPage.getTotalElements(),
            departamentoPage.getTotalPages()
        );
    }

    public DepartamentoResponse actualizar(Long id, DepartamentoUpdateRequest request) {
        Departamento departamento = departamentoDAO.findById(id)
            .orElseThrow(() -> new DepartamentoNotFoundException(id));

        departamento.setNombre(request.nombre());
        departamento.setDescripcion(request.descripcion());

        Departamento saved = departamentoDAO.save(departamento);
        return toResponse(saved);
    }

    public void eliminar(Long id) {
        Departamento departamento = departamentoDAO.findById(id)
            .orElseThrow(() -> new DepartamentoNotFoundException(id));

        if (empleadoRepository.existsByDepartamentoId(id)) {
            throw new DepartamentoInUseException(id);
        }

        departamentoDAO.delete(departamento);
    }

    private DepartamentoResponse toResponse(Departamento departamento) {
        return new DepartamentoResponse(
            departamento.getId(),
            departamento.getNombre(),
            departamento.getDescripcion()
        );
    }
}