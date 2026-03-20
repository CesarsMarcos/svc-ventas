package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import com.svc.ventas.models.mapstruct.mappers.CategoriaMapper;
import com.svc.ventas.util.SecurityUtils;
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

	private final SecurityUtils securityUtils;

	@Override
	public List<CategoriaDto> lista() {
		return categoriaRepo.listaActivos()
				.stream()
				.map(categoriaMapper::mapToGetDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<CategoriaDto> categoriasPorProductoStock() {
		Usuario usuario = securityUtils.obtenerUsuarioLogueado();
		return categoriaRepo.listaPorCategoriaProducto(usuario.getEmpleado().getSucursal().getIdSucursal())
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
