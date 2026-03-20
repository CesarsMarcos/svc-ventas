package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SerieDTO {

  private Long idSerie;

  private String tipoDocumento;

  private String serie;

  private String correlativo;

  private String sucursal;

  private String createdBy;

  private LocalDateTime fecAdd;

  private Boolean estado;

}
