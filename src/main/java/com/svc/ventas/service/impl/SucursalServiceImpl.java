package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.message.request.SucursalRequest;
import com.svc.ventas.models.dao.EmpresaRepository;
import com.svc.ventas.models.entity.Empresa;
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

	private final EmpresaRepository empresaRepo;
	
	private final SucursalMapper sucursalMapper;

	private final AppContext appContext;

	@Override
	public List<SucursalDto> lista() {
		Long idEmpresa = appContext.getEmpresaId();

		return sucursalRepo.findSucursalesPorEmpresa(idEmpresa)
				.stream()
				.map(sucursalMapper::mapToSucursalDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Response agregar(SucursalRequest sucursal) {

		Empresa empresa = appContext.getEmpresa();

		Long cantidadSucursales = sucursalRepo.cantidadSucursal(empresa.getIdEmpresa());

		if(Long.valueOf(empresa.getNumeroSucursales()).compareTo(cantidadSucursales) == 0){
			throw new BusinessException("Haz registrado las sucursales que tienes permitida en tu subscripción");
		}

		sucursalRepo.save(sucursalMapper.mapRequestToSucursalPost(sucursal, empresa));

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
