package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tipo_documento", foreignKey = @ForeignKey(name = "fk_serie_tipo_documento"))
  private TipoDocumento tipoDocumento;

  private String serie;

  private int correlativo;

  @Column(name="ind_estado")
  private Boolean indEstado;

}
