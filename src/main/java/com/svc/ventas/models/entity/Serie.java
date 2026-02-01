package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

  @JsonIgnore
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sucursal", foreignKey = @ForeignKey(name = "fk_serie_sucursal"))
  private Sucursal sucursal;

  private String serie;

  private Integer correlativo;

  @Column(name="ind_estado")
  private Boolean indEstado;

  @Column(name = "created_by")
  private String  createdBy;

  @Column(name = "updated_by")
  private String  updatedBy;

}
