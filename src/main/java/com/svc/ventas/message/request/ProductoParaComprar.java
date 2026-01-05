package com.svc.ventas.message.request;

import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import com.svc.ventas.models.mapstruct.dto.MarcaDto;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.models.mapstruct.dto.UnidadMedidaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoParaComprar extends ProductoDTO {

  private Integer cantidad;

  private Integer cantidadRecibida;

  private BigDecimal precioCompra;

  public ProductoParaComprar(Long idProducto,
                             @NotNull CategoriaDto categoria, @NotNull MarcaDto marca,
                             @NotNull UnidadMedidaDto unidadMedida, @NotBlank String descripcion,
                             @NotBlank String nombre, String imagen, @NotNull BigDecimal precio, Boolean indEstado) {
    super(idProducto, categoria, marca, unidadMedida, descripcion, nombre, imagen, precio, indEstado);
  }
}
