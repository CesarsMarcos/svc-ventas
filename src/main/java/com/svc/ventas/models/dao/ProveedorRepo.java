package com.svc.ventas.models.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.Proveedor;

public interface ProveedorRepo extends JpaRepository<Proveedor, Long> {
	
	List<Proveedor> findAll();

}
