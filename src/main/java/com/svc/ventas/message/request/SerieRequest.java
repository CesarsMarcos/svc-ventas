package com.svc.ventas.message.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SerieRequest {

  @NotNull(message = "La Sucursal es obligatoria")
  private Long idSucursal;

  @NotNull(message = "El Tipo Documento es obligatorio")
  private Long idTipoDocumento;

  @NotBlank(message = "La serie es obligatoria")
  @Size(min = 3, max = 10, message = "La serie debe tener entre 3 y 10 caracteres")
  @Pattern(
          regexp = "^[A-Za-z0-9]+$",
          message = "La serie solo puede contener letras y números"
  )
  private String serie;

  @NotNull(message = "El correlativo es obligatorio")
  private Long correlativo;

}
