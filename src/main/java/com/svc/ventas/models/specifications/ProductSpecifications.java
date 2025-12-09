package com.svc.ventas.models.specifications;

import com.svc.ventas.models.entity.Categoria;
import com.svc.ventas.models.entity.Producto;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

  public static Specification<Producto> hasName(String name) {
    return (root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")),"%" + name.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("codigo")), "%" + name.toLowerCase() + "%"));
  }

  public static Specification<Producto> hasCategory(Long categoryId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoria").get("idCategoria"), categoryId);
  }

  public static Specification<Producto> hasStatus(Boolean status) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("indEstado"), status);
  }





}
