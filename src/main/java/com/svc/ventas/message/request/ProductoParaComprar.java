package com.svc.ventas.message.request;

import com.svc.ventas.models.mapstruct.dto.CategoriaGetDto;
import com.svc.ventas.models.mapstruct.dto.MarcaGetDto;
import com.svc.ventas.models.mapstruct.dto.ProductoPostDTO;
import com.svc.ventas.models.mapstruct.dto.UnidadMedidaGetDto;
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
public class ProductoParaComprar extends ProductoPostDTO {

  private Integer cantidad;

  public ProductoParaComprar(Long idProducto,
                             @NotNull CategoriaGetDto categoria, @NotNull MarcaGetDto marca,
                             @NotNull UnidadMedidaGetDto unidadMedida, @NotBlank String descripcion,
                             @NotBlank String nombre, String imagen, @NotNull BigDecimal precio,
                             @NotNull BigDecimal precioDescuento, @NotNull BigDecimal precioProveedor,
                             @NotNull Integer maxCantidad, @NotNull Integer minCantidad, @NotNull Integer stock) {
    super(idProducto, categoria, marca, unidadMedida, descripcion, nombre, imagen, precio,
            precioDescuento, precioProveedor, maxCantidad, minCantidad, stock);
  }
}
