package com.svc.ventas.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_tipo_documentos")
public class TipoDocumento implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_tipo_documento")
  private Long idTipoDocumento;

  @Column(name = "codigo_sunat", nullable = false)
  private String codigoSunat;

  private String descripcion;

  @Column(name = "tipo_operacion")
  private String tipoOperacion;

  @Column(name = "genera_serie")
  private Boolean generaSerie;

  private Boolean indEstado;

}
