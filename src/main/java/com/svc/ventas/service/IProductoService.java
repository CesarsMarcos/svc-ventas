package com.svc.ventas.service;

import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.ProductoRequest;
import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IProductoService {

	List<ProductoDTO> lista();

	@Transactional
	Response agregar (ProductoRequest producto);

	@Transactional
	Response modificar(Long id, ProductoRequest producto);

	ProductoDetailsDTO details(Long id);

	ProductoDTO obtener(Long id) ;

	void eliminar(Long id);

  Map<String, Object> searchProductos(String nombre, Integer catergoriaId,
																			Boolean estado, int page, int size);

	List<ProductoSearchResponse> buscarPorNombreOCodigo(String termino);

	Page<ProductoSearchResponse> buscarPorNombreOCodigo(String termino, Pageable pageable);

	Map<String, Object> searchProductsSales(String nombre, int page, int size);

	void updateEstado(Long idProducto);

}
