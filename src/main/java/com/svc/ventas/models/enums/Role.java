package com.svc.ventas.models.enums;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Role {

  ROLE_VENDEDOR(Set.of(
          Permission.VER_PRODUCTOS,
          Permission.VER_PRODUCTO_STOCK,

          Permission.VER_CLIENTES,
          Permission.CREAR_CLIENTES,

          Permission.VER_VENTAS,
          Permission.REGISTRAR_VENTA,

          Permission.VER_CAJA,
          Permission.APERTURAR_CAJA,
          Permission.CERRAR_CAJA
  )),

  ROLE_SUPERVISOR(inherit(
          ROLE_VENDEDOR,

          Permission.VER_COMPRAS,
          Permission.REGISTRAR_COMPRA,

          Permission.VER_PROVEEDORES,
          Permission.CREAR_PROVEEDORES,
          Permission.EDITAR_PROVEEDORES,

          Permission.VER_INVENTARIO,
          Permission.EDITAR_PRODUCTO_STOCK,
          Permission.AJUSTAR_PRECIOS,

          Permission.VER_KARDEX,

          Permission.VER_REPORTES
  )),

  ROLE_ADMIN(inherit(
          ROLE_SUPERVISOR,

          Permission.VER_PERSONAS,
          Permission.CREAR_PERSONAS,
          Permission.EDITAR_PERSONAS,
          Permission.ELIMINAR_PERSONAS,

          Permission.VER_EMPLEADOS,
          Permission.CREAR_EMPLEADOS,
          Permission.EDITAR_EMPLEADOS,
          Permission.ELIMINAR_EMPLEADOS,
          Permission.EDITAR_ESTADO_EMPLEADOS,

          Permission.VER_USUARIOS,
          Permission.CREAR_USUARIOS,
          Permission.EDITAR_USUARIOS,
          Permission.ELIMINAR_USUARIOS,

          Permission.VER_PRODUCTOS,
          Permission.CREAR_PRODUCTOS,
          Permission.EDITAR_PRODUCTOS,
          Permission.ELIMINAR_PRODUCTOS,

          Permission.VER_CATEGORIAS,
          Permission.CREAR_CATEGORIAS,
          Permission.EDITAR_CATEGORIAS,
          Permission.ELIMINAR_CATEGORIAS,

          Permission.VER_SERIES,
          Permission.CREAR_SERIES,
          Permission.EDITAR_SERIES,
          Permission.ELIMINAR_SERIES,
          Permission.TIPO_DOCUMENTOS_SERIES,
          Permission.VER_SUCURSALES
  )),

  ROLE_SUPER_ADMIN(inherit(
          ROLE_ADMIN,

          Permission.EDITAR_ESTADO_USUARIOS,

          Permission.VER_EMPRESAS,
          Permission.GESTIONAR_EMPRESAS,

          Permission.CREAR_SUCURSALES,
          Permission.EDITAR_SUCURSALES,
          Permission.ELIMINAR_SUCURSALES

  ));

  private final Set<Permission> permissions;

  Role(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  private static Set<Permission> inherit(Role role, Permission... extraPermissions) {
    Set<Permission> permissions = new HashSet<>(role.getPermissions());
    permissions.addAll(Set.of(extraPermissions));
    return Collections.unmodifiableSet(permissions);
  }
}
