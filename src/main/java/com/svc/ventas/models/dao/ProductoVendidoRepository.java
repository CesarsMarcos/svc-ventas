package com.svc.ventas.models.dao;

import com.svc.ventas.models.mapstruct.dto.ProductoMasVendidoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.ProductoVendido;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductoVendidoRepository extends JpaRepository<ProductoVendido, Integer> {

  @Query("""
          SELECT new com.svc.ventas.models.mapstruct.dto.ProductoMasVendidoDTO(
          pv.nombre,
          SUM(pv.cantidad),
          SUM(pv.cantidad * pv.precio),
          (SELECT p.indEstado FROM Producto p WHERE p.idProducto = pv.idProducto))
          FROM ProductoVendido pv
          INNER JOIN Venta v ON v.idVenta = pv.venta.idVenta
          WHERE v.fecAdd >= :inicio AND v.fecAdd < :fin
          AND v.sucursal.idSucursal = :idSucursal
          GROUP BY pv.idProducto, pv.nombre
          ORDER BY 2 desc
          """)
  List<ProductoMasVendidoDTO> obtenerTop10ProductosMasVendidos(LocalDateTime inicio, LocalDateTime fin, Long idSucursal);

}
