package com.dsw01.practica02.service;

import com.dsw01.practica02.domain.Departamento;
import com.dsw01.practica02.domain.Empleado;
import com.dsw01.practica02.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.dto.EmpleadoPageResponse;
import com.dsw01.practica02.dto.EmpleadoResponse;
import com.dsw01.practica02.dto.EmpleadoUpdateRequest;
import com.dsw01.practica02.exception.DepartamentoNotFoundException;
import com.dsw01.practica02.exception.EmpleadoNotFoundException;
import com.dsw01.practica02.exception.UsernameAlreadyExistsException;
import com.dsw01.practica02.repository.DepartamentoDAO;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private static final int PAGE_SIZE = 5;

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoDAO departamentoDAO;
    private final ClaveEmpleadoGenerator claveEmpleadoGenerator;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoService(
        EmpleadoRepository empleadoRepository,
        DepartamentoDAO departamentoDAO,
        ClaveEmpleadoGenerator claveEmpleadoGenerator,
        PasswordEncoder passwordEncoder
    ) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoDAO = departamentoDAO;
        this.claveEmpleadoGenerator = claveEmpleadoGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    public EmpleadoResponse crear(EmpleadoCreateRequest request) {
        if (empleadoRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }

        Departamento departamento = departamentoDAO.findById(request.departamentoId())
            .orElseThrow(() -> new DepartamentoNotFoundException(request.departamentoId()));

        Empleado empleado = new Empleado();
        empleado.setClave(claveEmpleadoGenerator.nextClave());
        empleado.setNombre(request.nombre());
        empleado.setDireccion(request.direccion());
        empleado.setTelefono(request.telefono());
        empleado.setUsername(request.username().trim());
        empleado.setPasswordHash(passwordEncoder.encode(request.password()));
        empleado.setDepartamento(departamento);

        Empleado saved = empleadoRepository.save(empleado);
        return toResponse(saved);
    }

    public EmpleadoResponse obtenerPorClave(String clave) {
        Empleado empleado = empleadoRepository.findById(clave)
            .orElseThrow(() -> new EmpleadoNotFoundException(clave));
        return toResponse(empleado);
    }

    public EmpleadoPageResponse listar(int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by("clave").ascending());
        Page<Empleado> empleadoPage = empleadoRepository.findAll(pageRequest);
        return new EmpleadoPageResponse(
            empleadoPage.getContent().stream().map(this::toResponse).toList(),
            empleadoPage.getNumber(),
            empleadoPage.getSize(),
            empleadoPage.getTotalElements(),
            empleadoPage.getTotalPages()
        );
    }

    public EmpleadoResponse actualizar(String clave, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(clave)
            .orElseThrow(() -> new EmpleadoNotFoundException(clave));

        if (request.username() != null && !request.username().isBlank()) {
            String normalizedUsername = request.username().trim();
            boolean usernameChanged = !normalizedUsername.equalsIgnoreCase(empleado.getUsername());
            if (usernameChanged && empleadoRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
                throw new UsernameAlreadyExistsException(normalizedUsername);
            }
            empleado.setUsername(normalizedUsername);
        }

        if (request.password() != null && !request.password().isBlank()) {
            empleado.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        Departamento departamento = departamentoDAO.findById(request.departamentoId())
            .orElseThrow(() -> new DepartamentoNotFoundException(request.departamentoId()));

        empleado.setNombre(request.nombre());
        empleado.setDireccion(request.direccion());
        empleado.setTelefono(request.telefono());
        empleado.setDepartamento(departamento);

        Empleado saved = empleadoRepository.save(empleado);
        return toResponse(saved);
    }

    public void eliminar(String clave) {
        Empleado empleado = empleadoRepository.findById(clave)
            .orElseThrow(() -> new EmpleadoNotFoundException(clave));
        empleadoRepository.delete(empleado);
    }

    private EmpleadoResponse toResponse(Empleado empleado) {
        return new EmpleadoResponse(
            empleado.getClave(),
            empleado.getNombre(),
            empleado.getDireccion(),
            empleado.getTelefono(),
            empleado.getDepartamento().getId(),
            empleado.getUsername()
        );
    }
}
