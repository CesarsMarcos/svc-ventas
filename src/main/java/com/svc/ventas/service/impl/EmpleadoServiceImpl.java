package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.EmpleadoRepo;
import com.svc.ventas.models.entity.Empleado;
import com.svc.ventas.models.mapstruct.mappers.EmpleadoMapper;
import com.svc.ventas.service.IEmpleadoService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements IEmpleadoService {

	private final EmpleadoRepo empleadoRepo;
	
	private final EmpleadoMapper empleadoMapper;

	private final PersonaMapper personaMapper;

	@Override
	public List<EmpleadoDto> lista() {
		return empleadoRepo.findEmpleados()
				.stream()
				.map(empleadoMapper::mapToEmpleadoDto)
				.collect(Collectors.toList());
	}

	@Override
	public Response agregar(EmpleadoDto empleado) {
		empleadoRepo.save(empleadoMapper.mapToEmpleado(empleado));
		return Response.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(int id, EmpleadoDto empleadoPostDto) {
		empleadoRepo.findById(id)
				.map(empleado-> {
					empleado.setPersona(personaMapper.mapToPersona(empleadoPostDto.getPersona()));
					return empleadoRepo.save(empleado);
				})
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", id)));
		return Response.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public EmpleadoDto obtener(int id) {
		return empleadoRepo.findById(id)
				.map(empleadoMapper::mapToEmpleadoDto)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", id)));
	}

	@Override
	public void eliminar(int id) {
		Empleado empleadoSave = empleadoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", id)));
		empleadoSave.setIndEstado(Constantes.IND_INACTIVO);
		empleadoRepo.save(empleadoSave);
	}

	@Override
	public Boolean isSaved(String documento) {
		return empleadoRepo.existsByPersonaNumDocumento(documento);
	}

}
