package com.svc.ventas.models.dao;

import java.util.List;

import com.svc.ventas.message.response.SearchProductoCompra;
import com.svc.ventas.models.entity.ProductoStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.svc.ventas.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductoRepo extends CrudRepository<Producto, Long>,
				JpaSpecificationExecutor<Producto> {

	@Query("SELECT a FROM Producto a WHERE a.indEstado = true")
	List<Producto> listaActivos();

	List<Producto> findByNombreContaining(String nombre);

	@Query("""
          SELECT ps FROM ProductoStock ps
          WHERE ps.producto.codigo = :termino
             OR LOWER(ps.producto.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))
          """)
	List<ProductoStock> buscarPorNombreOCodigo(String termino);


	@Query("""
					SELECT new com.svc.ventas.message.response.SearchProductoCompra(
					p.idProducto,
					p.nombre,
					COALESCE(ps.precioVenta,0),
					COALESCE(ps.stock, 0),
					p.imagen)
					FROM Producto p
					LEFT JOIN ProductoStock ps
					ON ps.producto.idProducto = p.idProducto
					AND ps.sucursal.id = :idSucursal
					WHERE (:termino IS NULL OR TRIM(:termino) = '' OR
					p.codigo = :termino OR
					LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%')))
					""")
	Page<SearchProductoCompra> buscarProductosParaCompra(@Param("termino") String termino, @Param("idSucursal") Long idSucursal, Pageable pageable);

}
