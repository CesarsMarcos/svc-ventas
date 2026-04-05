package com.svc.ventas.models.dao;

import java.util.List;

import com.svc.ventas.models.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.svc.ventas.models.entity.Categoria;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepo extends JpaRepository<Categoria, Integer>{

	@Query("SELECT c FROM Categoria c WHERE c.indEstado = true AND c.empresa = :empresa")
	List<Categoria> categoriasActivasPorEmpresa(@Param("empresa") Empresa empresa);

	@Query("""
					SELECT DISTINCT c
					FROM Categoria c
					JOIN Producto p ON p.categoria = c
					JOIN ProductoStock ps ON ps.producto = p
					WHERE ps.sucursal.id = :idSucursal
					AND ps.stock > 0
					""")
	List<Categoria> listaCategoriaPorProducto(@Param("idSucursal") Long idSucursal);
	
}
