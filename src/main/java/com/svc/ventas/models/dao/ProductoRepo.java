package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Producto;
import org.springframework.data.repository.query.Param;

public interface ProductoRepo extends JpaRepository<Producto, Long>{

	@Query("SELECT a FROM Producto a WHERE a.indEstado = true")
	public List<Producto> listaActivos();

	List<Producto> findByNombreContaining(String nombre);

	@Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
					"OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :termino, '%'))")
	List<Producto> buscarPorNombreOCodigo(@Param("termino") String termino);
	
	
	
}
