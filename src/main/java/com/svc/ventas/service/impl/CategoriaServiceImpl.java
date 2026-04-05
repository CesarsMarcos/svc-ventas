package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import com.svc.ventas.models.mapstruct.mappers.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.CategoriaRepo;
import com.svc.ventas.service.ICategoriaService;
import com.svc.ventas.util.Constantes;

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
