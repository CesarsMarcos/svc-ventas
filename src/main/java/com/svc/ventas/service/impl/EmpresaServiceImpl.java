package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import com.svc.ventas.message.request.EmpresaPostRequest;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaGetDto;
import com.svc.ventas.models.mapstruct.mappers.Empresamapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.EmpresaRepository;
import com.svc.ventas.service.IEmpresaService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements IEmpresaService {

  private final EmpresaRepository empresaRepo;

  private final Empresamapper empresaMapper;

  @Override
  public List<EmpresaGetDto> listar() {
    return empresaRepo.findAll()
            .stream()
            .map(empresaMapper::mapToDto)
            .collect(Collectors.toList());
  }

  @Override
  public EmpresaGetDto obtener(Integer id) {
    return empresaRepo.findById(id)
            .map(empresaMapper::mapToGetDto)
            .orElseThrow(() ->
                    new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empresa", id)));
  }

  @Override
  public Response guardar(EmpresaPostRequest request) {

    if(request.getAplicaImpuesto().equals(Boolean.TRUE)){
      request.setNombreImpuesto("");
      request.setPorcentajeImpuesto(Double.parseDouble("0.00"));
    }

    empresaRepo.save(empresaMapper.mapToEntity(request));
    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public Response modificar(Integer id, EmpresaPostRequest request) {

    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_MOD)
            .build();
  }

  @Override
  public Response cambiarAplicacionImpuesto(Integer id, Boolean aplica) {
    Empresa empresaSave = empresaRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empresa", id)));
    empresaSave.setAplicaImpuesto(aplica);
    log.info("Cambia el estado para la aplicación de impuesto a {}", aplica);
    empresaRepo.save(empresaSave);

    String cambio = (aplica.equals(Boolean.TRUE)) ? "habilito" : "deshabilito";

    return Response.builder()
            .mensaje("Se ".concat(cambio)
                    .concat(" la aplicación de impuesto. Debes iniciar sessión nuevamente para activar los cambios"))
            .build();
  }

  @Override
  public void eliminar(Integer id) {
    Empresa empresaSave = empresaRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empresa", id)));
    empresaSave.setIndEstado(Constantes.IND_INACTIVO);
    empresaRepo.save(empresaSave);
  }

}
