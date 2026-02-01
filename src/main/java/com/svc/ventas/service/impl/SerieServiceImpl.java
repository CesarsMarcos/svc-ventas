package com.svc.ventas.service.impl;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.SerieRepository;
import com.svc.ventas.models.entity.Serie;
import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.enums.TipoPago;
import com.svc.ventas.models.mapstruct.dto.SerieDTO;
import com.svc.ventas.service.ISerieService;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements ISerieService {

  private final SerieRepository serieRepo;

  @Override
  public List<Serie> series() {
    return serieRepo.findAll();
  }

  @Override
  public Response save(Serie serie) {
    serie.setIndEstado(Constantes.IND_ACTIVO);
    serieRepo.save(serie);

    return Response.builder()
            .mensaje("Serie creada con éxito")
            .build();
  }

  @Override
  public Serie get(Long id) {
    return null;
  }

  @Override
  public SerieDTO getByIdDocumentType(Long idSucursal, TipoDocumento tipoDocumento) {
    return serieRepo.findBySucursalIdSucursalAndTipoDocumento(idSucursal, tipoDocumento)
            .map(serie -> SerieDTO.builder()
                       .serie(serie.getSerie())
                       .correlativo(AppUtils.formatearSunat(serie.getCorrelativo() + 1))
                       .build()
            )
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Serie", tipoDocumento)));
  }
}
