package com.svc.ventas.models.specifications;

import com.svc.ventas.models.entity.Persona;
import org.springframework.data.jpa.domain.Specification;

public class PersonaSpecifications {
  public static Specification<Persona> hasNombre(String nombre) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nombre"), nombre);
  }

  public static Specification<Persona> hasDocumento(String documento) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("numDocumento"), documento);
  }
}
