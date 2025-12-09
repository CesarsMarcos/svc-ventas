package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductoRepo extends CrudRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {

	@Query("SELECT a FROM Producto a WHERE a.indEstado = true")
	public List<Producto> listaActivos();

	List<Producto> findByNombreContaining(String nombre);

	@Query("""
					SELECT p FROM Producto p
					WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))
					  OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :termino, '%'))
					""")
	Page<Producto> buscarPorNombreOCodigo(@Param("termino") String termino, Pageable pageable);



}
