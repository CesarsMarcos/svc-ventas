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
@Table(name="tb_marcas")
public class Marca implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id_marca")
	private Integer idMarca;

	@ManyToOne
	@JoinColumn(name = "id_empresa", foreignKey=@ForeignKey(name="fk_marca_empresa"))
	private Empresa empresa;

	private String descripcion;

	private Boolean indEstado;

	@Column(name = "created_by")
	private String  createdBy;

	@Column(name = "updated_by")
	private String  updatedBy;

}