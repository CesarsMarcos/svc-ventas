package com.svc.ventas.models.dao;

import com.svc.ventas.models.mapstruct.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.Venta;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepo extends JpaRepository<Venta, Long>,
        JpaSpecificationExecutor<Venta>, PagingAndSortingRepository<Venta, Long> {

  @Query("""
          SELECT v
          FROM Venta v
          WHERE v.cliente.persona.numDocumento =:documentoCliente
          """)
  List<Venta> ventasPorDocumentoCliente(@Param("documentoCliente") String documentoCliente);

  @Query("""
          SELECT COALESCE(SUM(v.total), 0)
          FROM Venta v
          WHERE v.fecAdd BETWEEN :inicio AND :fin
          AND v.sucursal.idSucursal = :idSucursal
          """)
  BigDecimal obtenerSumaVentasPorRango(LocalDateTime inicio, LocalDateTime fin, Long idSucursal);

  @Query("""
          SELECT COUNT(v.idVenta)
          FROM Venta v
          WHERE v.fecAdd BETWEEN :inicio AND :fin
          AND v.sucursal.idSucursal = :idSucursal
          """)
  Long countVentas(LocalDateTime inicio, LocalDateTime fin, Long idSucursal);

  @Query("""
          SELECT new com.svc.ventas.models.mapstruct.dto.VentasPorMesDTO(
          YEAR(v.fecAdd), MONTH(v.fecAdd), SUM(v.total))
          FROM Venta v
          WHERE v.fecAdd >= :fechaInicio
          GROUP BY YEAR(v.fecAdd), MONTH(v.fecAdd)
          ORDER BY YEAR(v.fecAdd), MONTH(v.fecAdd)
          """)
  List<VentasPorMesDTO> obtenerVentasUltimos12Meses(LocalDateTime fechaInicio);

  @Query("""
          SELECT new com.svc.ventas.models.mapstruct.dto.BajoStockDTO(
          p.nombre,
          ps.stock)
          FROM ProductoStock ps
          INNER JOIN Producto p
          ON ps.producto.idProducto = p.idProducto
          WHERE ps.estado = TRUE
          AND ps.sucursal.idSucursal =:idSucursal
          AND ps.stock <= 5
          ORDER BY ps.stock desc
          LIMIT 5
          """)
  List<BajoStockDTO> obtenerProductosBajoStock(Long idSucursal);

  @Query("""
          SELECT new com.svc.ventas.models.mapstruct.dto.UltimasVentasDTO(
          v.idVenta, v.cliente.persona.nombres,
          v.tipoDocumento.descripcion, v.fecAdd,
          v.total)
          FROM Venta v
          WHERE v.sucursal.idSucursal = :idSucursal
          ORDER BY v.total desc
          LIMIT 5
          """)
  List<UltimasVentasDTO> obtenerUltimas5Ventas(Long idSucursal);


  @Query("""
           SELECT DISTINCT new com.svc.ventas.models.mapstruct.dto.ProductoSearchVentaDto(
                   ps.idProductoStock,
                   psp.idPresentacion,
                   ps.producto.nombre,
                   ps.producto.marca.descripcion,
                   psp.nombre,
                   psp.isPrincipal,
                   psp.precioVenta,
                   ps.stock)
          FROM ProductoStock ps
          JOIN ps.presentaciones psp
          WHERE ps.estado = true
          AND ps.sucursal.idSucursal = :sucursalId
          AND psp.estado = true
            AND (
                  LOWER(COALESCE(ps.producto.codigo, '')) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
               OR LOWER(TRIM(ps.producto.nombre)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%')))
            AND ps.sucursal.idSucursal = :sucursalId
          """)
  List<ProductoSearchVentaDto> buscarPorNombreOCodigoPresentacionesParaVenta(
          @Param("termino") String termino,
          @Param("sucursalId") Long sucursalId);

  @Query(value = """
           SELECT DISTINCT new com.svc.ventas.models.mapstruct.dto.ProductoSearchVentaDto(
                   ps.idProductoStock,
                   psp.idPresentacion,
                   ps.producto.nombre,
                   ps.producto.marca.descripcion,
                   psp.nombre,
                   psp.isPrincipal,
                   psp.precioVenta,
                   ps.stock)
          FROM ProductoStock ps
          JOIN ps.presentaciones psp
          WHERE ps.estado = true AND psp.estado = true
            AND
                (:categoriaId IS NULL OR ps.producto.categoria.idCategoria = :categoriaId)
            AND (
                  LOWER(COALESCE(ps.producto.codigo, '')) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
               OR LOWER(TRIM(ps.producto.nombre)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%')))
            AND ps.sucursal.idSucursal = :sucursalId
          """,
          countQuery = """
                  SELECT COUNT(ps)
                  FROM ProductoStock ps
                  JOIN ps.presentaciones psp
                  WHERE ps.estado = true AND psp.estado = true
                    AND
                        (:categoriaId IS NULL OR ps.producto.categoria.idCategoria = :categoriaId)
                    AND (
                          LOWER(COALESCE(ps.producto.codigo, '')) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
                       OR LOWER(TRIM(ps.producto.nombre)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%')))
                    AND ps.sucursal.idSucursal = :sucursalId
                  """)
  Page<ProductoSearchVentaDto> buscarPorNombreOCodigoPresentacionesParaVentaPos(
          @Param("termino") String termino,
          @Param("categoriaId") Long categoriaId,
          @Param("sucursalId") Long sucursalId,
          Pageable pageable);

}

