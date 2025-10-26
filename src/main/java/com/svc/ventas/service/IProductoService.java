package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IProductoService {

	List<ProductoDTO> lista();
	
	List<ProductoSearchResponse>  listaParaCompra();

	Page<ProductoSearchResponse>  searchProductoPorNombre(String nombre, Pageable pageable);

	List<ProductoSearchResponse> buscarPorNombreOCodigo(String termino);

	@Transactional
	Response agregar (ProductoDTO productoDto);

	@Transactional
	Response modificar(Long id, ProductoDTO productoDto);

	ProductoDTO obtener(Long id) ;
	
	void eliminar(Long id);

}
