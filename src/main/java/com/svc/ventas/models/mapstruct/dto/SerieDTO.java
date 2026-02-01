package com.svc.ventas.models.mapstruct.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SerieDTO {

  private String serie;
  private String correlativo;

}
