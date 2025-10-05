package com.svc.ventas.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{

    Boolean existsByNumDocumento(String dni);
}
