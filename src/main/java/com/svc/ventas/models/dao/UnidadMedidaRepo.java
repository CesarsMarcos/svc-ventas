package com.svc.ventas.models.dao;

import java.util.List;

import com.svc.ventas.models.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.UnidadMedida;
import org.springframework.data.repository.query.Param;

public interface UnidadMedidaRepo extends JpaRepository<UnidadMedida, Integer> {

	@Query("SELECT u FROM UnidadMedida u WHERE u.indEstado = true AND u.empresa = :empresa")
	List<UnidadMedida> unidades(@Param("empresa") Empresa empresa);
	
}
