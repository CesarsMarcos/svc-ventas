package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Sucursal;

public interface SucursalRepo extends JpaRepository<Sucursal, Long>{

	@Query("SELECT s FROM Sucursal s WHERE s.empresa.idEmpresa = :idEmpresa AND s.indEstado= true")
	List<Sucursal> findSucursalesPorEmpresa(Long idEmpresa);

}
