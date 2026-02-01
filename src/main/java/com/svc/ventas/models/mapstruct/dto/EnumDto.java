package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class EnumDto {

  private String label;

  private String value;

}
