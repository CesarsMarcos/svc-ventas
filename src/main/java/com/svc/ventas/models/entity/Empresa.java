package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_empresas")
public class Empresa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_empresa")
	private Long idEmpresa;

	private String ruc;

	private String razonSocial;

	private String nombreComercial;

	private String direccion;

	private String email;

	private String telefono;

	private String departamento;

	private String provincia;

	private String distrito;

	private String ubigeo;

	@Column(name="nombre_impuesto")
	private String nombreImpuesto;

	@Column(name="porcentaje_impuesto")
	private double porcentajeImpuesto;

	@Column(name="simbolo_moneda")
	private String simboloMoneda;

	private String logo;

	@Column(name="ind_estado")
	private Boolean indEstado;

	@Column(name = "created_by")
	private String  createdBy;

	@Column(name = "updated_by")
	private String  updatedBy;

}