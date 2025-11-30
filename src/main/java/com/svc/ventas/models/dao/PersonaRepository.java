package com.svc.ventas.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.Persona;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{

    Boolean existsByNumDocumento(String dni);

    @Query("""
    SELECT p
    FROM Persona p
    WHERE NOT EXISTS (
        SELECT 1 FROM Empleado e WHERE e.persona.id = p.id
    )""")
    List<Persona> findDisponiblesParaEmpleado();

    @Query("""
    SELECT p
    FROM Persona p
    WHERE NOT EXISTS (
        SELECT 1 FROM Cliente c WHERE c.persona.id = p.id
    )""")
    List<Persona> findPersonasQueNoSonClientes();


}
