package com.svc.ventas.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProveedorRepo extends JpaRepository<Proveedor, Long>,
				JpaSpecificationExecutor<Proveedor> {

	Boolean existsBynumDocumento(String numDocumento);

	@Query("SELECT COUNT(p) FROM Proveedor p WHERE p.indEstado = true")
	Long numProveedoresActivos();

}
