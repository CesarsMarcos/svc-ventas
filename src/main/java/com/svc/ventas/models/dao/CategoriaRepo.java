package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Categoria;

public interface CategoriaRepo extends JpaRepository<Categoria, Integer>{

	@Query("SELECT c FROM Categoria c WHERE c.indEstado = true ")
	List<Categoria> listaActivos();
	
}
