package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.MarcaDto;
import org.springframework.stereotype.Service;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.MarcaRepo;
import com.svc.ventas.models.entity.Marca;
import com.svc.ventas.models.mapstruct.mappers.MarcaMapper;
import com.svc.ventas.service.IMarcaService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements IMarcaService {

	private final MarcaRepo marcaRepositorio;
	
	private final MarcaMapper marcaMapper;

	private final AppContext appContext;
	
	@Override
	public List<MarcaDto> lista() {
		Empresa empresa = appContext.getEmpresa();
		return marcaRepositorio.marcas(empresa)
				.stream()
				.map(marcaMapper::mapMarcaGetDto).collect(Collectors.toList());
	}

	@Override
	public MarcaDto obtener(Integer id) {
		return marcaRepositorio.findById(id)
				.map(marcaMapper::mapMarcaGetDto)
				.orElseThrow(()-> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND,"Marca",id)));
	}

	@Override
	public Response guardar(MarcaDto marcaDto) {
		marcaRepositorio.save(marcaMapper.mapMarca(marcaDto));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Integer id, MarcaDto marcaDto) {
		Marca marcaSave = marcaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Marca", id)));

		marcaSave.setDescripcion(marcaDto.getDescripcion());
		marcaRepositorio.save(marcaSave);

		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public void eliminar(int id) {
		Marca marcaSave = marcaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Marca", id)));

		marcaSave.setIndEstado(Constantes.IND_INACTIVO);
		marcaRepositorio.save(marcaSave);
	}

}
