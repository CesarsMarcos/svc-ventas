package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Marca;

public interface MarcaRepo extends JpaRepository<Marca, Integer>{

	@Query("SELECT m FROM Marca m WHERE m.indEstado = true")
	List<Marca> marcas();
	
}
