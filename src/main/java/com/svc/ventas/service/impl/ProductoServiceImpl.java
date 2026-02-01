package com.svc.ventas.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.svc.ventas.message.request.ProductoRequest;
import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.models.specifications.ProductStockSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.mappers.ProductoMapper;
import com.svc.ventas.service.IProductoService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

	private final ProductoRepo productoRepo;

	private final ProductoStockRepo productoStockRepo;

	private final ProductoMapper productoMapper;

	private final MarcaRepo marcaRepo;

	private final UnidadMedidaRepo unidadMedidaRepo;

	private final CategoriaRepo categoriaRepo;

	@Override
	public List<ProductoDTO> lista() {
		return productoRepo.listaActivos()
				.stream()
				.map(productoMapper::map)
				.collect(Collectors.toList());
	}

	@Override
	public Response agregar(ProductoRequest producto) {
		log.info("Iniciando registro de producto...");

		log.info("Obtener Marca ::");
		Marca marca = marcaRepo.findById(producto.getIdMarca())
						.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Marca",
										producto.getIdMarca())));;

		log.info("Obtener Categoria ::");
		Categoria categoria = categoriaRepo.findById(producto.getIdCategoria())
						.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Categoria",
										producto.getIdCategoria())));

		log.info("Obtener Unidad Medida ::");
		UnidadMedida unidadMedida = unidadMedidaRepo.findById(producto.getIdUnidadMedida())
						.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "UnidadMedida",
										producto.getIdUnidadMedida())));

		productoRepo.save(productoMapper.mapToProducto(producto, marca,categoria, unidadMedida));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Long id, ProductoRequest producto) {

		log.info("Iniciando modificación de producto...");

		log.info("Valida si existe producto...");
		Producto productoBD = productoRepo.findById(id)
						.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));

		log.info("Obtener Marca ::");
		Marca marca = marcaRepo.findById(producto.getIdMarca())
						.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Marca",
										producto.getIdMarca())));;

		log.info("Obtener Categoria ::");
		Categoria categoria = categoriaRepo.findById(producto.getIdCategoria())
						.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Categoria",
										producto.getIdCategoria())));

		log.info("Obtener Unidad Medida ::");
		UnidadMedida unidadMedida = unidadMedidaRepo.findById(producto.getIdUnidadMedida())
						.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "UnidadMedida",
										producto.getIdUnidadMedida())));

		productoBD.setNombre(producto.getNombre());
		productoBD.setDescripcion(producto.getDescripcion());
		productoBD.setImagen(producto.getImagen());
		productoBD.setMarca(marca);
		productoBD.setCategoria(categoria);
		productoBD.setUnidadMedida(unidadMedida);
		productoRepo.save(productoBD);

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
	public Map<String, Object> searchProductos(String nombre, Integer categoriaId,
																						 Boolean estado, int page, int size) {

		Specification<ProductoStock> spec =  Specification.where(null);

		if(Objects.nonNull(nombre) && !nombre.isEmpty()) {
			spec = spec.and(ProductStockSpecifications.hasName(nombre));
		}

		if(Objects.nonNull(categoriaId)){
			spec = spec.and(ProductStockSpecifications.hasCategory(categoriaId));
		}

		if(Objects.nonNull(estado)){
			spec = spec.and(ProductStockSpecifications.hasStatus(estado));
		}

		Pageable pageable = PageRequest.of(page, size);

		Page<ProductoStock> pageProductos = productoStockRepo.findAll(spec, pageable);

		List<ProductoSearchResponse> listProducts = pageProductos.getContent()
						.stream()
						.map(productoMapper::mapProductoStock)
						.toList();

		return Map.of(
						"products", listProducts,
						"currentPage", pageProductos.getNumber(),
						"pageSize", pageProductos.getSize(),
						"totalItems", pageProductos.getTotalElements(),
						"totalPages", pageProductos.getTotalPages(),
						"empty", pageProductos.isEmpty()
		);
	}

	@Override
	public Map<String, Object> searchProductsSales(String nombre, int page, int size) {

		String filtro = (nombre != null && !nombre.isBlank()) ? nombre.trim().toLowerCase() : "";

		Pageable pageable = PageRequest.of(page, size);

		Page<ProductoSearchResponse> pageProductos =
						buscarPorNombreOCodigo(filtro, pageable);

		return Map.of(
						"productos", pageProductos.getContent(),
						"currentPage", pageProductos.getNumber(),
						"pageSize", pageProductos.getSize(),
						"totalItems", pageProductos.getTotalElements(),
						"totalPages", pageProductos.getTotalPages(),
						"empty", pageProductos.isEmpty()
		);
	}

	@Override
	public List<ProductoSearchResponse> buscarPorNombreOCodigo(String termino) {
		if (termino == null || termino.isEmpty()) {
			return List.of();
		}
		return productoStockRepo.buscarPorNombreOCodigo(termino)
						.stream().map(productoMapper::mapToSearch)
						.collect(Collectors.toList());
	}

	public Page<ProductoSearchResponse> buscarPorNombreOCodigo(String termino, Pageable pageable) {
		return productoStockRepo.buscarPorNombreOCodigoPage(termino, pageable)
						.map(productoMapper::mapToSearch);
	}

}
