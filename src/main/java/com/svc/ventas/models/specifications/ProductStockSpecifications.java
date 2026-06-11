package com.svc.ventas.models.specifications;

import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.entity.Sucursal;
import org.springframework.data.jpa.domain.Specification;

public class ProductStockSpecifications {

  public static Specification<ProductoStock> hasCodigo(String codigo) {
    return (root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("producto").get("codigo")), "%" + codigo.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("producto").get("codigo")), "%" + codigo.toLowerCase() + "%"));
  }

  public static Specification<ProductoStock> hasName(String name) {
    return (root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("producto").get("nombre")), "%" + name.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("producto").get("nombre")), "%" + name.toLowerCase() + "%"));
  }

  public static Specification<ProductoStock> hasCategory(Integer categoryId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("producto").get("categoria").get("idCategoria"), categoryId);
  }

  public static Specification<ProductoStock> hasStatus(Boolean status) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("estado"), status);
  }

  public static Specification<ProductoStock> hasSucursal(Sucursal sucursal) {
    return (root, query, cb) -> cb.equal(root.get("sucursal"), sucursal);
  }

}
