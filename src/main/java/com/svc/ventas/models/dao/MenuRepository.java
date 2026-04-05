package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

  @Query(value = """
          SELECT
              m.id_menu,
              m.nombre,
              m.icono,
              m.url,
              m.id_menu_padre,
              m.id_empresa,
              m.is_empleado
          FROM tb_menu_rol mr
          INNER JOIN tb_usuario_rol ur ON ur.id_rol = mr.id_rol
          INNER JOIN tb_menus m ON m.id_menu = mr.id_menu
          INNER JOIN tb_usuarios u ON u.id_usuario = ur.id_usuario
          WHERE u.usuario = :username
          ORDER BY COALESCE(m.id_menu_padre, m.id_menu), m.id_menu;
          """, nativeQuery = true)
  List<Menu> listarMenuPorUsuario(@Param("username") String nombre);

}
