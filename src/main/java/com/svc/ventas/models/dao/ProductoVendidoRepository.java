package com.svc.ventas.models.dao;

import com.svc.ventas.models.mapstruct.dto.ProductoMasVendidoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.ProductoVendido;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoVendidoRepository extends JpaRepository<ProductoVendido, Integer> {

  @Query("SELECT new com.svc.venta.models.mapstruct.dto.ProductoMasVendidoDTO(" +
          "pv.nombre, " +
          "SUM(pv.cantidad), " +
          "SUM(pv.cantidad * pv.precio),  " +
          "(SELECT p.stock FROM Producto p WHERE p.idProducto = pv.idProducto), " +
          "(SELECT p.indEstado FROM Producto p WHERE p.idProducto = pv.idProducto)) " +
          "FROM ProductoVendido pv " +
          "GROUP BY pv.idProducto, pv.nombre")
  List<ProductoMasVendidoDTO> obtenerTop10ProductosMasVendidos();

}
