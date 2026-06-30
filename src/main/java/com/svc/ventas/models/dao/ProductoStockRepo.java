package com.svc.ventas.models.dao;

import com.svc.ventas.message.response.ResumenProductoResponse;
import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.mapstruct.dto.PresentacionesCompraDto;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoStockRepo extends CrudRepository<ProductoStock, Long>,
        JpaSpecificationExecutor<ProductoStock> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("""
          SELECT ps
          FROM ProductoStock ps
          WHERE ps.idProductoStock = :productoId
            AND ps.sucursal.id = :sucursalId AND ps.estado = true
          """)
  Optional<ProductoStock> buscar(Long productoId, Long sucursalId);

  @Query("""
          SELECT ps
          FROM ProductoStock ps
          WHERE ps.producto.codigo = :termino
            OR LOWER(ps.producto.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) AND ps.estado = true
          """)
  Page<ProductoStock> buscarPorNombreOCodigoPage(@Param("termino") String termino, Pageable pageable);

  @Query("""
          SELECT new com.svc.ventas.models.mapstruct.dto.PresentacionesCompraDto(
           ps.idPresentacion,
           ps.nombre,
           ps.equivalencia,
           ps.precioVenta
          )
          FROM ProductoStockPresentacion ps
          WHERE ps.productoStock.idProductoStock = :idProducto AND ps.estado = true
          """)
  List<PresentacionesCompraDto> presentacionesPorIdProducto(@Param("idProducto") Long idProducto);

  @Query("""
        SELECT new com.svc.ventas.message.response.ResumenProductoResponse(
            COUNT(ps),
            COALESCE(SUM(ps.costoPromedio * ps.stock),0),
            SUM(CASE WHEN ps.stock < 5 THEN 1 ELSE 0 END),
            COUNT(DISTINCT ps.producto.categoria))
        FROM ProductoStock ps
        JOIN ps.presentaciones psp
        WHERE ps.sucursal.idSucursal =:idSucursal
        AND ps.estado = true
        AND psp.isPrincipal = true
    """)
  ResumenProductoResponse obtenerResumen(@Param("idSucursal") Long idSucursal);

}
