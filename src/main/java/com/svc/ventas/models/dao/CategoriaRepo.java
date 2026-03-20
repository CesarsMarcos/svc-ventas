package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Categoria;

public interface CategoriaRepo extends JpaRepository<Categoria, Integer>{

	@Query("SELECT c FROM Categoria c WHERE c.indEstado = true ")
	List<Categoria> listaActivos();

	@Query("""
					SELECT DISTINCT c
					FROM Categoria c
					JOIN Producto p ON p.categoria = c
					JOIN ProductoStock ps ON ps.producto = p
					WHERE ps.sucursal.id = :idSucursal
					AND ps.stock > 0
					""")
	List<Categoria> listaPorCategoriaProducto(Long idSucursal);
	
}
