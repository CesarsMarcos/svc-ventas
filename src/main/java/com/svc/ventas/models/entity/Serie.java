package com.svc.ventas.models.entity;

import com.svc.ventas.models.enums.TipoDocumento;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_series")
public class Serie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idSerie;

  @Column(name = "tipo_documento")
  @Enumerated(EnumType.STRING)
  private TipoDocumento tipoDocumento;

  private String serie;

  private int correlativo;

  @Column(name="ind_estado")
  private Boolean indEstado;

  @Column(name = "created_by")
  private String  createdBy;

  @Column(name = "updated_by")
  private String  updatedBy;

}
