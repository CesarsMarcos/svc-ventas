package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.SucursalRepo;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.mapstruct.mappers.SucursalMapper;
import com.svc.ventas.service.ISucursalService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalServiceImpl implements ISucursalService {

	private final SucursalRepo sucursalRepo;
	
	private final SucursalMapper sucursalMapper;

	@Override
	public List<SucursalDto> lista() {
		return sucursalRepo.findSucursales()
				.stream()
				.map(sucursalMapper::mapToSucursalDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Response agregar(SucursalDto sucursalDto) {
		sucursalRepo.save(sucursalMapper.mapToSucursalPost(sucursalDto));

		return Response.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(int id, SucursalDto sucursalDto) {
		sucursalRepo.findById(id)
				.map(sucursal -> {
					sucursal = sucursalMapper.mapToSucursalPost(sucursalDto);
					return sucursalRepo.save(sucursal);
				}).orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", id)));

		return Response.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public SucursalDto obtener(int id) {
		return sucursalRepo.findById(id)
				.map(sucursalMapper::mapToSucursalDTO)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", id)));
	}

	@Override
	public void eliminar(int id) {
		Sucursal sucursalSave = sucursalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", id)));

		sucursalSave.setIndEstado(Constantes.IND_INACTIVO);
		sucursalRepo.save(sucursalSave);
	}

}
