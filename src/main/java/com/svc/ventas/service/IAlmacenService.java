package com.svc.ventas.service;

import com.svc.ventas.message.request.PresentacionUpdateRequest;
import com.svc.ventas.models.mapstruct.dto.ProductoStockDetailsDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoStockPresentacionDto;

import java.util.List;
import java.util.Map;

public interface IAlmacenService {

  Map<String, Object> searchProductos(String nombre, Integer categoriaId,
                                      Boolean estado, int page, int size);

  ProductoStockDetailsDTO details(Long idProductoStock);

  List<ProductoStockPresentacionDto> presentacionesPorProductoStock(Long idProductoStock);

  void updatePrecioVentaPresentaciones(List<PresentacionUpdateRequest> presentaciones);

}

