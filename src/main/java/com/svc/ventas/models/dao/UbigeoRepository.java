package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Ubigeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UbigeoRepository extends JpaRepository<Ubigeo, Long> {

    @Query("SELECT DISTINCT u.departamento FROM Ubigeo u ORDER BY u.departamento")
    List<String> findDistinctDepartamentos();

    @Query("SELECT DISTINCT u.provincia AS nombre, u.provinciaInei AS ubigeo FROM Ubigeo u WHERE u.departamento = :departamento ORDER BY u.provincia")
    List<Map<String, String>> findProvinciasByDepartamento(@Param("departamento") String departamento);

    @Query("SELECT DISTINCT u.distrito AS nombre, u.ubigeoInei AS ubigeo FROM Ubigeo u WHERE u.provincia = :provincia ORDER BY u.distrito")
    List<Map<String, String>> findDistritosByProvincia(@Param("provincia") String provincia);
}
