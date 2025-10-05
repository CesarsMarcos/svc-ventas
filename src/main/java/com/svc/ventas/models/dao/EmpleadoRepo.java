package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Empleado;

public interface EmpleadoRepo extends JpaRepository<Empleado, Integer>{

	@Query("SELECT e FROM Empleado e WHERE e.indEstado = true")
	List<Empleado> findEmpleados();

	Boolean existsByPersonaNumDocumento(String documento);
}
