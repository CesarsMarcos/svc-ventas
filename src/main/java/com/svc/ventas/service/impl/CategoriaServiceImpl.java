package com.svc.ventas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import com.svc.ventas.models.mapstruct.mappers.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.CategoriaRepo;
import com.svc.ventas.service.ICategoriaService;
import com.svc.ventas.util.Constantes;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements ICategoriaService {

	private final CategoriaRepo categoriaRepo;

	private final CategoriaMapper categoriaMapper;

	private final AppContext appContext;

	@Override
	public List<CategoriaDto> lista() {
		Empresa empresa = appContext.getEmpresa();
		return categoriaRepo.categoriasActivasPorEmpresa(empresa)
				.stream()
				.map(categoriaMapper::mapToGetDto)
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, Object> searchCategorias(String nombre, Pageable pageable) {
		log.info("Obtiene usuario en sessión ::");
		Long idEmpresa = appContext.getEmpresaId();

		Page<CategoriaDto> pageCategoria = categoriaRepo.buscarCategoriaPorDescripcion(nombre, idEmpresa, pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("categorias", pageCategoria.getContent());
		response.put("currentPage", pageCategoria.getNumber());
		response.put("totalItems", pageCategoria.getTotalElements());
		response.put("totalPages", pageCategoria.getTotalPages());

		return response;
	}

	@Override
	public List<CategoriaDto> categoriasPorProductoStock() {
		Sucursal sucursal = appContext.getSucursal();
		return categoriaRepo.listaCategoriaPorProducto(sucursal.getIdSucursal())
						.stream()
						.map(categoriaMapper::mapToGetDto)
						.collect(Collectors.toList());
	}


	@Override
	public Response guardar(CategoriaDto categoria) {
		categoriaRepo.save(categoriaMapper.mapToEntity(categoria));
		return Response.builder().mensaje(Constantes.MENSAJE_SAVE).build();
	}

	@Override
	public Response modificar(int id, CategoriaDto categoriaDto) {
		categoriaRepo.findById(id)
				.map(categoria -> {
					categoria.setDesCategoria(categoriaDto.getDesCategoria());
					return categoriaRepo.save(categoria);
				})
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Categoria", id)));
		return Response.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public CategoriaDto obtener(int id) {
		return categoriaRepo.findById(id)
				.map(categoriaMapper::mapToGetDto)
				.orElseThrow(() ->new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND,"Categoria",id)));
	}

}
