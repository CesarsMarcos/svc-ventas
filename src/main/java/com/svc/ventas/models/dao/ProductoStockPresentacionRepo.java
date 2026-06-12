package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.entity.ProductoStockPresentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoStockPresentacionRepo extends JpaRepository<ProductoStockPresentacion, Long> {

  @Query("""
          SELECT psp FROM ProductoStockPresentacion psp
          WHERE psp.productoStock =:productoStock
          AND psp.estado = true
          """)
  List<ProductoStockPresentacion> findByProductoStock(ProductoStock productoStock);

}
