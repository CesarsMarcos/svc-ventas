package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.message.request.SucursalRequest;
import com.svc.ventas.models.dao.EmpresaRepository;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import com.svc.ventas.util.SecurityUtils;
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

	private final EmpresaRepository empresaRepo;
	
	private final SucursalMapper sucursalMapper;

	private final SecurityUtils securityUtils;

	@Override
	public List<SucursalDto> lista() {
		Usuario usuarioLogueado = securityUtils.obtenerUsuarioLogueado();
		return sucursalRepo.findSucursales(usuarioLogueado.getEmpresa().getIdEmpresa())
				.stream()
				.map(sucursalMapper::mapToSucursalDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Response agregar(SucursalRequest sucursal) {
		Empresa empresaSave = empresaRepo.findById(sucursal.getIdEmpresa())
						.orElseThrow(() ->
										new EntityNotFoundException(String.
														format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", sucursal.getIdEmpresa())));

		sucursalRepo.save(sucursalMapper.mapRequestToSucursalPost(sucursal, empresaSave));

		return Response.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Long id, SucursalDto sucursalDto) {
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
	public SucursalDto obtener(Long id) {
		return sucursalRepo.findById(id)
				.map(sucursalMapper::mapToSucursalDTO)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", id)));
	}

	@Override
	public void eliminar(Long id) {
		Sucursal sucursalSave = sucursalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", id)));

		sucursalSave.setIndEstado(Constantes.IND_INACTIVO);
		sucursalRepo.save(sucursalSave);
	}

}
