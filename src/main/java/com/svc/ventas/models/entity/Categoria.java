package com.svc.ventas.models.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_categorias")
public class Categoria implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCategoria;

	@ManyToOne
	@JoinColumn(name = "id_empresa", foreignKey=@ForeignKey(name="fk_categoria_empresa"))
	private Empresa empresa;

	@NotBlank
	private String desCategoria;

	private Boolean indEstado;

	@Column(name = "created_by")
	private String  createdBy;

	@Column(name = "updated_by")
	private String  updatedBy;

}