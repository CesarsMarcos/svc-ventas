package com.svc.ventas.service;

import com.svc.ventas.models.mapstruct.dto.ProductoStockDetailsDTO;

import java.math.BigDecimal;
import java.util.Map;

public interface IAlmacenService {

  Map<String, Object> searchProductos(String nombre, Integer categoriaId,
                                      Boolean estado, int page, int size);

  ProductoStockDetailsDTO details (Long idProductoStock);

  void updatePrecioVenta(Long idProductoStock, BigDecimal precioVenta);

}

