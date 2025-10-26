package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.models.mapstruct.mappers.CategoriaMapper;
import com.svc.ventas.models.mapstruct.mappers.MarcaMapper;
import com.svc.ventas.models.mapstruct.mappers.UMedidaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.ProductoRepo;
import com.svc.ventas.models.entity.Producto;
import com.svc.ventas.models.mapstruct.mappers.ProductoMapper;
import com.svc.ventas.service.IProductoService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

	private final ProductoRepo productoRepo;

	private final ProductoMapper productoMapper;

	private final MarcaMapper marcaMapper;

	private final CategoriaMapper categoriaMapper;

	private final UMedidaMapper uMedidaMapper;
	

	@Override
	public List<ProductoDTO> lista() {
		return productoRepo.listaActivos()
				.stream()
				.map(productoMapper::map)
				.collect(Collectors.toList());
	}

	@Override
	public Response agregar(ProductoDTO productoDto) {
		productoRepo.save(productoMapper.mapToProducto(productoDto));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Long id, ProductoDTO productoDto) {
		productoRepo.findById(id)
				.map(producto ->{
					producto.setNombre(productoDto.getNombre());
					producto.setDescripcion(productoDto.getDescripcion());
					producto.setMaxCantidad(productoDto.getMaxCantidad());
					producto.setMinCantidad(productoDto.getMinCantidad());
					producto.setImagen(productoDto.getImagen());
					producto.setPrecio(productoDto.getPrecio());
					producto.setStock(productoDto.getStock());
					producto.setMarca(marcaMapper.mapMarca(productoDto.getMarca()));
					producto.setCategoria(categoriaMapper.mapToCategoria(productoDto.getCategoria()));
					producto.setUnidadMedida(uMedidaMapper.mapToUnidadMedida(productoDto.getUnidadMedida()));
					return productoRepo.save(producto);
				}).orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));

		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public ProductoDTO obtener(Long id) {
		return productoRepo.findById(id)
				.map(productoMapper::map)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));
	}

	@Override
	public void eliminar(Long id) {
		Producto productoSave = productoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));

		productoSave.setIndEstado(Constantes.IND_INACTIVO);
		productoRepo.save(productoSave);
	}

	@Override
	public Page<ProductoSearchResponse> searchProductoPorNombre(String nombre, Pageable pageable) {
		final Page<ProductoSearchResponse> contratoSearch;

		List<ProductoSearchResponse> productos = productoRepo.findByNombreContaining(nombre)
				.stream()
				.map(productoMapper::mapToSearch)
				.collect(Collectors.toList());

		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), productos.size());

		if (!CollectionUtils.isEmpty(productos)) {
			contratoSearch = new PageImpl<>(productos.subList(start, end), pageable, productos.size());
		} else {
			contratoSearch = new PageImpl<>(productos, pageable, 0);
		}
		return contratoSearch;
	}

	public List<ProductoSearchResponse> buscarPorNombreOCodigo(String termino) {
		if (termino == null || termino.isEmpty()) {
			return List.of();
		}
		return productoRepo.buscarPorNombreOCodigo(termino)
						.stream().map(productoMapper::mapToSearch)
						.collect(Collectors.toList());
	}

	@Override
	public List<ProductoSearchResponse> listaParaCompra() {
		return productoRepo.listaActivos()
				.stream()
				.map(productoMapper::mapToSearch)
				.collect(Collectors.toList());
	}

}
