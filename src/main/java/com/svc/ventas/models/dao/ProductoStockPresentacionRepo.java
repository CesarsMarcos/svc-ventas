package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.entity.ProductoStockPresentacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoStockPresentacionRepo extends JpaRepository<ProductoStockPresentacion, Long> {

  List<ProductoStockPresentacion> findByProductoStock(ProductoStock productoStock);

}
