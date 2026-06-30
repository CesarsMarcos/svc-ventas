package com.svc.ventas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.ConflictException;
import com.svc.ventas.message.request.ClienteCreateParaVentaRequest;
import com.svc.ventas.message.request.ClienteCreateRequest;
import com.svc.ventas.message.response.ClienteSaveResponse;
import com.svc.ventas.message.response.ResponseData;
import com.svc.ventas.models.dao.PersonaRepository;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.enums.TipoDocumentoPersona;
import com.svc.ventas.models.mapstruct.dto.ClienteDto;
import com.svc.ventas.models.mapstruct.dto.ClienteSelectedDto;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
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
  public List<ClienteSelectedDto> clientesParaVenta() {
    Empresa empresa = appContext.getEmpresa();
    return clienteRepo.clientesActivosPorEmpresa(empresa)
            .stream()
            .map(clienteMapper::mapToClienteVenta)
            .collect(Collectors.toList());
  }

  @Override
  public Response agregar(ClienteCreateRequest clienteRequest) {

    Persona persona = personaRepo.findById(clienteRequest.getIdPersona())
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Persona", clienteRequest.getIdPersona())));

    if (clienteRepo.existsByPersonaIdPersona(clienteRequest.getIdPersona())) {
      throw new ConflictException("El cliente ya fue registrado");
    }

    clienteRepo.save(clienteMapper.mapCliente(clienteRequest, persona));
    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public ResponseData<ClienteSaveResponse> agregarParaVenta(ClienteCreateParaVentaRequest clienterRequest) {

    Empresa empresa = appContext.getEmpresa();

    log.info(":: Valida que documento no este registrado {}", clienterRequest.getNumDocumento());
    if (personaRepo.existsBynumDocumento(clienterRequest.getNumDocumento())) {
      throw new ConflictException("El cliente ya fue registrado");
    }

    log.info(":: Registra persona con documento {}", clienterRequest.getNumDocumento());
    Persona.PersonaBuilder builder = Persona.builder()
            .tipoDocumento(clienterRequest.getTipoDocumento())
            .numDocumento(clienterRequest.getNumDocumento())
            .indEstado(Boolean.TRUE)
            .empresa(empresa);
    if (clienterRequest.getTipoDocumento() == TipoDocumentoPersona.RUC) {
      builder.razonSocial(clienterRequest.getRazonSocial());
    } else {
      builder.nombres(clienterRequest.getNombre())
              .apePaterno(clienterRequest.getApePaterno())
              .apeMaterno(clienterRequest.getApeMaterno());
    }

    Persona personaNew = builder.build();
    personaNew = personaRepo.save(personaNew);

    log.info(":: Registra cliente con documento {}", clienterRequest.getNumDocumento());
    Cliente clienteNew = Cliente.builder()
            .persona(personaNew)
            .isClienteGenerico(Boolean.FALSE)
            .indEstado(Boolean.TRUE)
            .build();

    clienteNew = clienteRepo.save(clienteNew);

    return ResponseData.<ClienteSaveResponse>builder()
            .data(ClienteSaveResponse.builder()
                    .idCliente(clienteNew.getIdCliente())
                    .numDocumento(personaNew.getNumDocumento())
                    .nombreCompleto(personaNew.getNombreMostrado()).build())
            .mensaje(Constantes.MENSAJE_SAVE).build();
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
  public Page<Cliente> searchCliente(String termino, Pageable pageable) {
    Specification<Cliente> spec = Specification.where(null);

    if (termino != null && !termino.isEmpty()) {
      spec = spec.and(ClienteSpecifications.search(termino));
    }
    return clienteRepo.findAll(spec, pageable);
  }

  @Override
  public Map<String, Object> searchClientesParaVenta(String termino, Pageable pageable) {
    Specification<Cliente> spec = Specification.where(null);
    if (termino != null && !termino.isEmpty()) {
      spec = spec.and(ClienteSpecifications.search(termino));
    }

    Page<Cliente> pageCliente = clienteRepo.findAll(spec, pageable);

    List<ClienteSelectedDto> clientesDto = pageCliente.getContent()
            .stream()
            .map(clienteMapper::mapToClienteVenta)
            .toList();

    Map<String, Object> response = new HashMap<>();
    response.put("clientes", clientesDto);
    response.put("currentPage", pageCliente.getNumber());
    response.put("totalItems", pageCliente.getTotalElements());
    response.put("totalPages", pageCliente.getTotalPages());

    return response;

  }

  @Override
  public ClienteSelectedDto getClienteFinal() {
    return clienteMapper.mapToClienteVenta(clienteRepo.getCienteFinal());
  }

}
