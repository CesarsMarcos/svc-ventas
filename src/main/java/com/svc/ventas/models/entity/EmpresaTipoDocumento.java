package com.svc.ventas.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_empresa_tipo_documento")
public class EmpresaTipoDocumento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_empresa")
  private Empresa empresa;

  @ManyToOne
  @JoinColumn(name = "id_tipo_documento")
  private TipoDocumento tipoDocumento;

  private Boolean indEstado;

}
