package com.svc.ventas.models.dao;

import java.util.List;

import com.svc.ventas.models.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Marca;
import org.springframework.data.repository.query.Param;

public interface MarcaRepo extends JpaRepository<Marca, Integer>{

	@Query("SELECT m FROM Marca m WHERE m.indEstado = true AND m.empresa = :empresa")
	List<Marca> marcas(@Param("empresa")Empresa empresa);
	
}
