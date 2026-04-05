package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.ConflictException;
import com.svc.ventas.message.request.ClienteCreateRequest;
import com.svc.ventas.models.dao.PersonaRepository;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.mapstruct.dto.ClienteDto;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.ClienteRepo;
import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.mapstruct.mappers.ClienteMapper;
import com.svc.ventas.models.specifications.ClienteSpecifications;
import com.svc.ventas.service.IClienteService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

	private final ClienteRepo clienteRepo;

	private final PersonaRepository personaRepo;
	
	private final ClienteMapper clienteMapper;

	private final PersonaMapper personaMapper;

	private final AppContext appContext;

	@Override
	public List<ClienteDto> clientes() {
		Empresa empresa = appContext.getEmpresa();
		return clienteRepo.clientesActivosPorEmpresa(empresa)
				.stream()
				.map(clienteMapper::mapClienteDto)
				.collect(Collectors.toList());
	}

	@Override
	public Response agregar(ClienteCreateRequest clienteRequest) {

		Persona persona = personaRepo.findById(clienteRequest.getIdPersona())
						.orElseThrow(() -> new EntityNotFoundException(
										String.format(Constantes.MENSAJE_NOT_FOUND, "Persona", clienteRequest.getIdPersona())));

		if(clienteRepo.existsByPersonaIdPersona(clienteRequest.getIdPersona())){
			throw new ConflictException("El cliente ya fue registrado");
		}

		clienteRepo.save(clienteMapper.mapCliente(clienteRequest, persona));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Integer id, ClienteDto clienteDto) {
		clienteRepo.findById(id)
				.map(cliente -> {
					cliente.setPersona(personaMapper.mapToPersona(clienteDto.getPersona()));
					return clienteRepo.save(cliente);
				})
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Cliente", id)));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public ClienteDto obtener(Integer id) {
		return clienteRepo.findById(id)
				.map(clienteMapper::mapClienteDto)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Cliente", id)));
	}

	@Override
	public Page<Cliente> searchCliente(String documento, String nombre,  Pageable pageable) {
		Specification<Cliente> spec =  Specification.where(null);
		if(documento != null && !documento.isEmpty()) {
			spec = spec.and(ClienteSpecifications.hasClienteDocumento(documento));	
		}
		if(nombre != null && !nombre.isEmpty()) {
			spec = spec.and(ClienteSpecifications.hasClienteNombre(nombre));
		}
		return clienteRepo.findAll(spec,pageable);
	}

}
