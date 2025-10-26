package com.svc.ventas.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_ubigeos")
public class Ubigeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubigeo")
    private Integer idUbigeo;

    @Column(name = "ubigeo_reniec", length = 10)
    private String ubigeoReniec;

    @Column(name = "ubigeo_inei", length = 10)
    private String ubigeoInei;

    @Column(name = "departamento_inei")
    private Integer departamentoInei;

    @Column(name = "departamento", length = 50)
    private String departamento;

    @Column(name = "provincia_inei")
    private Integer provinciaInei;

    @Column(name = "provincia", length = 50)
    private String provincia;

    @Column(name = "distrito", length = 50)
    private String distrito;

}
