package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Compra;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.mapstruct.dto.ProductoSearchCompraDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long>,
        JpaSpecificationExecutor<Compra> {

  Boolean existsBySerieAndCorrelativoAndSucursal(String serie, Long correlativo, Sucursal sucursal);

  @Query("SELECT COUNT(c.idCompra) FROM Compra c WHERE c.fecAdd BETWEEN :inicio AND :fin")
  Long countCompras (LocalDateTime inicio, LocalDateTime fin);

  @Query("""
           SELECT DISTINCT new com.svc.ventas.models.mapstruct.dto.ProductoSearchCompraDto(
                   ps.idProductoStock,
                   psp.idPresentacion,
                   ps.producto.nombre,
                   psp.nombre,
                   psp.isPrincipal,
                   psp.precioVenta,
                   ps.stock)
          FROM ProductoStock ps
          JOIN ps.presentaciones psp
          WHERE psp.isPrincipal = true
            AND ps.sucursal.id = :sucursalId
            AND (
                  LOWER(COALESCE(ps.producto.codigo, '')) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
               OR LOWER(TRIM(ps.producto.nombre)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
            )
          """)
  List<ProductoSearchCompraDto> buscarPorNombreOCodigoPresentaciones(
          @Param("termino") String termino,
          @Param("sucursalId") Long sucursalId);

  @Query(value = """
           SELECT DISTINCT new com.svc.ventas.models.mapstruct.dto.ProductoSearchCompraDto(
                   ps.idProductoStock,
                   psp.idPresentacion,
                   ps.producto.nombre,
                   psp.nombre,
                   psp.isPrincipal,
                   psp.precioVenta,
                   ps.stock)
          FROM ProductoStock ps
          JOIN ps.presentaciones psp
          WHERE psp.isPrincipal = true
            AND ps.sucursal.id = :sucursalId
            AND (
                  LOWER(COALESCE(ps.producto.codigo, '')) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
               OR LOWER(TRIM(ps.producto.nombre)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
            )
          """,
          countQuery = """
          SELECT COUNT(ps)
           FROM ProductoStock ps
          JOIN ps.presentaciones psp
          WHERE psp.isPrincipal = true
            AND ps.sucursal.id = :sucursalId
            AND (
                  LOWER(COALESCE(ps.producto.codigo, '')) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
               OR LOWER(TRIM(ps.producto.nombre)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
            )
          """)
  Page<ProductoSearchCompraDto> buscarPorNombreOCodigoPresentacionesPage(
          @Param("termino") String termino,
          @Param("sucursalId") Long sucursalId,
          Pageable pageable);

}
