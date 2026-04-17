package com.dsw01.practica02.repository;

import com.dsw01.practica02.domain.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DepartamentoDAO {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoDAO(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Optional<Departamento> findById(Long id) {
        return departamentoRepository.findById(id);
    }

    public Page<Departamento> findAll(Pageable pageable) {
        return departamentoRepository.findAll(pageable);
    }

    public void delete(Departamento departamento) {
        departamentoRepository.delete(departamento);
    }
}
