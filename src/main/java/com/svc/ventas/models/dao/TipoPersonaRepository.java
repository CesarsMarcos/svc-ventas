package com.svc.ventas.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.TipoPersona;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoPersonaRepository extends JpaRepository<TipoPersona, Integer>{

  @Query("SELECT tp FROM TipoPersona tp WHERE idTipoPersona IN(1,3)")
  List<TipoPersona> tiposSinPersona();

}
