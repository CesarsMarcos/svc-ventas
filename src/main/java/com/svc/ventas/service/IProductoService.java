package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.ProductoGetDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IProductoService {

	List<ProductoGetDTO> lista();
	
	List<ProductoSearchResponse>  listaParaCompra();

	Page<ProductoSearchResponse>  searchProductoPorNombre(String nombre, Pageable pageable);

	List<ProductoSearchResponse> buscarPorNombreOCodigo(String termino);

	@Transactional
	Response agregar (ProductoPostDTO productoDto);

	@Transactional
	Response modificar(Long id, ProductoPostDTO productoDto);

	ProductoGetDTO obtener(Long id) ;
	
	void eliminar(Long id);

}
