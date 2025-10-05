package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.ProveedorGetDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.models.dao.ProveedorRepo;
import com.svc.ventas.models.entity.Proveedor;
import com.svc.ventas.models.mapstruct.dto.ProveedorPostDto;
import com.svc.ventas.models.mapstruct.mappers.ProveedorMapper;
import com.svc.ventas.service.IProveedorService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements IProveedorService {

	private final  ProveedorRepo proveedorRepo;
	
	private final ProveedorMapper proveedorMapper;

	@Override
	public List<ProveedorGetDto> proveedores() {
		return proveedorRepo.findAll()
						.stream().map(proveedorMapper::mapToProveedorDto).collect(Collectors.toList());
	}

	public List<ProveedorSelectedDto> proveedoresListSelected() {
		return proveedorRepo.findAll()
				.stream().map(proveedorMapper::mapToProveedorSelected)
				.collect(Collectors.toList());
	}

	@Override
	public Response registrar(ProveedorPostDto proveedorDto) {
		proveedorRepo.save(proveedorMapper.mapToProveedor(proveedorDto));
		return Response.builder().mensaje(Constantes.MENSAJE_SAVE).build();
	}

	@Override
	public Response modificar(Integer id, ProveedorPostDto proveedorDto) {
		Proveedor proveedorSave = proveedorRepo.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Proveedor", id)));
		return Response.builder().mensaje(Constantes.MENSAJE_MOD).build();
	}

	@Override
	public ProveedorGetDto obtener(int id) {
		return proveedorRepo.findById(id)
				.map(proveedorMapper::mapToProveedorDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Proveedor", id)));
	}

	@Override
	public void eliminar(int id) {
		Proveedor proveedorSave = proveedorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Proveedor", id)));
		proveedorSave.setIndEstado(Constantes.IND_ACTIVO);
		proveedorRepo.save(proveedorSave);
	}

}
