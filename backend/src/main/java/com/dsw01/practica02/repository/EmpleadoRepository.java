package com.dsw01.practica02.repository;

import com.dsw01.practica02.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
	boolean existsByDepartamentoId(Long departamentoId);
	boolean existsByUsernameIgnoreCase(String username);
	Optional<Empleado> findByUsernameIgnoreCase(String username);
}
