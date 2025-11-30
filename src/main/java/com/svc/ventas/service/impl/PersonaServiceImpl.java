package com.svc.ventas.service.impl;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.PersonaRepository;
import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import com.svc.ventas.models.mapstruct.dto.PersonaListDto;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import com.svc.ventas.models.mapstruct.mappers.TipoDocumentoMapper;
import com.svc.ventas.service.IPersonaService;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements IPersonaService {

  private final PersonaRepository personaRepo;

  private final PersonaMapper personaMapper;

  private final TipoDocumentoMapper tipoDocMapper;

  @Override
  public List<PersonaDto> personas() {
    return personaRepo.findAll().stream()
            .map(personaMapper::map)
            .collect(Collectors.toList());
  }

  @Override
  public List<PersonaListDto> personasNoEmpleados() {
    return personaRepo.findDisponiblesParaEmpleado().stream()
            .map(personaMapper::mapToPersonaListDto)
            .collect(Collectors.toList());
  }

  @Override
  public List<PersonaListDto> personasNoClientes() {
    return personaRepo.findPersonasQueNoSonClientes().stream()
            .map(personaMapper::mapToPersonaListDto)
            .collect(Collectors.toList());
  }

  @Override
  public Response guardar(PersonaDto personaDto) {
    Persona persona = personaMapper.mapToPersona(personaDto);

    personaRepo.save(persona);
    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public Response modificar(Integer id, PersonaDto personaDto) {

    Persona personaSave = personaRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Persona", id)));

    personaSave.setNombre(personaDto.getNombre());
    personaSave.setApeMaterno(personaDto.getApeMaterno());
    personaSave.setApePaterno(personaDto.getApePaterno());
    personaSave.setCelular(personaDto.getCelular());
    personaSave.setCorreo(personaDto.getCorreo());
    personaSave.setDireccion(personaDto.getDireccion());
    personaSave.setFechaNacimiento(personaDto.getFechaNacimiento());
    personaSave.setFoto(personaDto.getFoto());
    personaSave.setNumDocumento(personaDto.getNumDocumento());
    personaSave.setTelefono(personaDto.getTelefono());
    personaSave.setTipoDocumento(tipoDocMapper.mapTipoDocumento(personaDto.getTipoDocumento()));
    personaRepo.save(personaSave);

    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_MOD)
            .build();
  }

  @Override
  public PersonaDto obtener(Integer id) {
    return personaRepo.findById(id)
            .map(personaMapper::map)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Persona", id)));
  }

  @Override
  public Boolean isSaved(String documento) {
    return personaRepo.existsByNumDocumento(documento);
  }


}
