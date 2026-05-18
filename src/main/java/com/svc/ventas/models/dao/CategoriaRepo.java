package com.svc.ventas.models.dao;

import java.util.List;

import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.svc.ventas.models.entity.Categoria;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepo extends JpaRepository<Categoria, Integer> {

  @Query("SELECT c FROM Categoria c WHERE c.indEstado = true AND c.empresa = :empresa")
  List<Categoria> categoriasActivasPorEmpresa(@Param("empresa") Empresa empresa);

  @Query("""
          SELECT DISTINCT c
          FROM Categoria c
          JOIN Producto p ON p.categoria = c
          JOIN ProductoStock ps ON ps.producto = p
          WHERE ps.sucursal.id = :idSucursal
          AND ps.stock > 0
          """)
  List<Categoria> listaCategoriaPorProducto(@Param("idSucursal") Long idSucursal);


  @Query(value = """
           SELECT new com.svc.ventas.models.mapstruct.dto.CategoriaDto(
                   cat.idCategoria,
                   cat.desCategoria,
                   cat.indEstado)
          FROM Categoria cat
          WHERE cat.indEstado = true
            AND (
                 LOWER(TRIM(cat.desCategoria)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
            ) AND cat.empresa.idEmpresa = :idEmpresa
          """,
          countQuery = """
                  SELECT COUNT(cat)
                   FROM Categoria cat
                  WHERE cat.indEstado = true
                    AND (
                       LOWER(TRIM(cat.desCategoria)) LIKE LOWER(CONCAT('%', TRIM(:termino), '%'))
                    ) AND cat.empresa.idEmpresa = :idEmpresa
                  """)
  Page<CategoriaDto> buscarCategoriaPorDescripcion(
          @Param("termino") String termino,
          @Param("idEmpresa") Long idEmpresa,
          Pageable pageable);

}
