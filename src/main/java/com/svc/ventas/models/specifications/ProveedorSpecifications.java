package com.svc.ventas.models.specifications;

import com.svc.ventas.models.entity.Proveedor;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class ProveedorSpecifications {
  public static Specification<Proveedor> hasRazonSocial(String razonSocial) {
    return (root, query, cb) -> {
      if (razonSocial == null) {
        return cb.conjunction();
      }
      Expression<String> termino =  root.get("razonSocial");
      return cb.like(cb.lower(termino), "%" + razonSocial.toLowerCase() + "%");
    };
  }

}
