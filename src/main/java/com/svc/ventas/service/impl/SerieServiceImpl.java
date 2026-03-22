package com.svc.ventas.service.impl;

import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.exception.ValidationException;
import com.svc.ventas.message.request.SerieRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.SerieRepository;
import com.svc.ventas.models.dao.SucursalRepo;
import com.svc.ventas.models.dao.TipoDocumentoRepository;
import com.svc.ventas.models.entity.Serie;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.CorrelativoDTO;
import com.svc.ventas.models.mapstruct.dto.SerieDTO;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDTO;
import com.svc.ventas.models.mapstruct.mappers.SerieMapper;
import com.svc.ventas.service.ISerieService;
import com.svc.ventas.util.Constantes;
import com.svc.ventas.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements ISerieService {

  private final SerieRepository serieRepo;

  private final SerieMapper serieMapper;

  private final TipoDocumentoRepository tipoDocumentoRepo;

  private final SucursalRepo sucursalRepo;

  private final SecurityUtils securityUtils;

  @Override
  public List<SerieDTO> series() {
    return serieRepo.findAll()
            .stream().map(serieMapper::toEntity)
            .collect(Collectors.toList());
  }

  @Override
  public Response save(SerieRequest serieRequest) {

    Usuario usuarioLogueado = securityUtils.obtenerUsuarioLogueado();

    Long empresaId = usuarioLogueado.getEmpresa().getIdEmpresa();

    Long sucursalId = usuarioLogueado.getEmpleado().getSucursal().getIdSucursal();

    TipoDocumento tipoDocumento = tipoDocumentoRepo.findById(serieRequest.getIdTipoDocumento())
            .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no válido"));

    Sucursal sucursalBD = sucursalRepo.findById(serieRequest.getIdSucursal())
            .orElseThrow(() -> new EntityNotFoundException("Sucursal no registrada"));


    if (!Boolean.TRUE.equals(tipoDocumento.getGeneraSerie())) {
      throw new BusinessException("El tipo de documento no permite generar series");
    }

    String prefijoEsperado = obtenerPrefijo(tipoDocumento.getCodigoSunat());

    validarSerie(serieRequest.getSerie(), prefijoEsperado, tipoDocumento.getDescripcion());

    boolean existe = serieRepo.existsBySerie(
            empresaId,
            sucursalBD.getIdSucursal(),
            tipoDocumento.getIdTipoDocumento(),
            serieRequest.getSerie()
    );

    if (existe) {
      throw new ValidationException("La serie ya existe para este tipo de documento en la sucursal");
    }

    serieRepo.save(Serie.builder()
            .empresa(usuarioLogueado.getEmpresa())
            .sucursal(sucursalBD)
            .tipoDocumento(tipoDocumento)
            .serie(serieRequest.getSerie())
            .correlativo(serieRequest.getCorrelativo())
            .createdBy(usuarioLogueado.getUsuario())
            .indEstado(Constantes.IND_ACTIVO)
            .build());

    return Response.builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public Serie get(Long id) {
    return null;
  }

  @Override
  public CorrelativoDTO getSeriePorIdIipoDocumento(Long idTipoDocumento) {
    Usuario usuarioLogueado = securityUtils.obtenerUsuarioLogueado();
    Serie serie = serieRepo.getSerie(usuarioLogueado.getEmpresa().getIdEmpresa(),
                    usuarioLogueado.getEmpleado().getSucursal().getIdSucursal(), idTipoDocumento)
            .orElseThrow(() -> new RuntimeException("No existe serie configurada"));

    CorrelativoDTO correlativoDTO = serieMapper.toCorrelativoDto(serie);
    correlativoDTO.setCorrelativo(correlativoDTO.getCorrelativo() + 1);

    return correlativoDTO;
  }

  @Override
  public List<TipoDocumentoDTO> getTipoDocumento() {
    Usuario usuarioLogueado = securityUtils.obtenerUsuarioLogueado();
    return tipoDocumentoRepo.tipoDocumentos(usuarioLogueado.getEmpresa().getIdEmpresa());

  }

  @Transactional
  public String generarNumero(Long empresaId, Long sucursalId, Long tipoDocumentoId) {

    Serie serie = serieRepo.obtenerSerieForUpdate(
                    empresaId, sucursalId, tipoDocumentoId)
            .orElseThrow(() -> new RuntimeException("No existe serie configurada"));

    Long nuevoCorrelativo = serie.getCorrelativo() + 1;

    serie.setCorrelativo(nuevoCorrelativo);

    String numeroFormateado = String.format("%s-%08d",
            serie.getSerie(),
            nuevoCorrelativo
    );

    return numeroFormateado;
  }

  private String obtenerPrefijo(String codigoSunat) {

    return switch (codigoSunat) {
      case "01" -> "F"; // Factura
      case "03" -> "B"; // Boleta
      default -> throw new BusinessException("Tipo de documento no soportado para series");
    };
  }

  private void validarSerie(String serie, String prefijoEsperado, String tipo) {

    if (serie == null || !serie.matches("^[FB]\\d{3}$")) {
      throw new BusinessException("Formato de serie inválido (Ej: F001, B001)");
    }

    String prefijo = serie.substring(0, 1);

    if (!prefijo.equalsIgnoreCase(prefijoEsperado)) {
      throw new BusinessException(
              String.format("La serie para %s debe iniciar con %s", tipo, prefijoEsperado)
      );
    }
  }

}
