package com.dsw01.practica02;

import com.dsw01.practica02.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.exception.UsernameAlreadyExistsException;
import com.dsw01.practica02.repository.DepartamentoDAO;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.ClaveEmpleadoGenerator;
import com.dsw01.practica02.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoUsernameUniqueIntegrationTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private DepartamentoDAO departamentoDAO;

    @Mock
    private ClaveEmpleadoGenerator claveEmpleadoGenerator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void crearEmpleadoConUsernameDuplicadoLanzaConflict() {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest(
            "Ana",
            "Calle 1",
            "555",
            "ana",
            "Password123",
            1L
        );

        when(empleadoRepository.existsByUsernameIgnoreCase(eq("ana"))).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> empleadoService.crear(request));
    }
}
