package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.mapstruct.dto.AlmacenParaCompraDto;
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
            AND ps.sucursal.id = :sucursalId
          """)
  Optional<ProductoStock> buscar(Long productoId, Long sucursalId);

  @Query("""
          SELECT ps
          FROM ProductoStock ps
          WHERE ps.producto.codigo = : termino
            OR LOWER(ps.producto.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))
          """)
  Page<ProductoStock> buscarPorNombreOCodigoPage(@Param("termino") String termino, Pageable pageable);

  @Query("""
          SELECT new com.svc.ventas.models.mapstruct.dto.PresentacionesCompraDto(
           ps.idPresentacion,
           ps.nombre,
           ps.equivalencia,
           ps.precioVenta
          )
          FROM ProductoStockPresentacion   ps
          WHERE  ps.productoStock.idProductoStock = :idProducto
          """)
  List<PresentacionesCompraDto> presentacionesPorIdProducto(@Param("idProducto") Long idProducto);

  @Query("""
          SELECT new com.svc.ventas.models.mapstruct.dto.AlmacenParaCompraDto(
            ps.idProductoStock,
            ps.producto.nombre)
            FROM ProductoStock ps
          WHERE ps.idProductoStock = :idProductoStock""")
  Optional<AlmacenParaCompraDto> obtenerProductoInventario(@Param("idProductoStock") Long idProductoStock);

}
