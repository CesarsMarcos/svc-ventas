package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaDto;
import com.svc.ventas.models.mapstruct.mappers.Empresamapper;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.EmpresaRepository;
import com.svc.ventas.service.IEmpresaService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements IEmpresaService {

	private final EmpresaRepository empresaRepo;

	private final Empresamapper empresaMapper;

	@Override
	public List<EmpresaDto> listar() {
		return empresaRepo.findAll()
						.stream()
						.map(empresaMapper::mapToDto)
						.collect(Collectors.toList());
	}

	@Override
	public EmpresaDto obtener(Integer id) {
		return empresaRepo.findById(id)
				.map(empresaMapper::mapToGetDto)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empresa", id)));

	}

	@Override
	public Response guardar(EmpresaDto empresaDto) {
		empresaRepo.save(empresaMapper.mapToEntity(empresaDto));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Integer id, EmpresaDto empresa) {
		 empresaRepo.findById(id)
				.map(empresaMapper::mapToDto)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empresa", id)));

		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public void eliminar(Integer id) {
		Empresa empresaSave = empresaRepo.findById(id)
						.orElseThrow(()-> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND,"Empresa",id)));
		empresaSave.setIndEstado(Constantes.IND_INACTIVO);
		empresaRepo.save(empresaSave);
	}

}
