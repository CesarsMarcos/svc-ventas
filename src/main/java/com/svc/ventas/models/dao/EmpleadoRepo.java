package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Empleado;
import org.springframework.data.repository.query.Param;

public interface EmpleadoRepo extends JpaRepository<Empleado, Integer>,
				JpaSpecificationExecutor<Empleado> {

	@Query("SELECT e FROM Empleado e WHERE e.indEstado = true")
	List<Empleado> findEmpleados();

	Boolean existsByPersonaNumDocumento(String documento);

	Boolean existsByPersonaIdPersona(Integer id);

	@Query("""
    SELECT e
    FROM Empleado e
    WHERE NOT EXISTS (
        SELECT 1 FROM Usuario u WHERE u.empleado.id = e.id)
        AND e.persona.numDocumento = :termino
        OR LOWER(CONCAT(e.persona.nombre, CONCAT(' ', e.persona.apePaterno)))
        LIKE LOWER(CONCAT('%', :termino, '%'))
    """)
	Page<Empleado> findEmpleadosQueNoTienenUsuario(@Param("termino") String termino, Pageable pageable);

	@Query("""
    SELECT e
    FROM Empleado e
    WHERE NOT EXISTS (
        SELECT 1 FROM Usuario u WHERE u.empleado.id = e.id
        )
    """)
	List<Empleado> empleadosQueNoTienenUsuario();
}
