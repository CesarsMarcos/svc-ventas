package com.svc.ventas.models.specifications;

import com.svc.ventas.models.entity.Persona;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class PersonaSpecifications {
  public static Specification<Persona> hasNombre(String nombre) {
    return (root, query, cb) -> {
      Expression<String> nombreCompleto = cb.concat(
              cb.concat(root.get("nombre"), " "), root.get("apePaterno")
      );
      return cb.like(
              cb.upper(nombreCompleto),
              "%" + nombre.toUpperCase() + "%"
      );
    };
  }

  public static Specification<Persona> hasDocumento(String documento) {
    return (root, query, cb) -> cb.equal(root.get("numDocumento"), documento);
  }
}
