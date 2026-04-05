package com.svc.ventas.util;

import java.math.BigDecimal;

public class Constantes {

  public static final String MENSAJE_SAVE = ":: Registro guardado con éxito";

  public static final String MENSAJE_MOD = ":: Registro modificado con éxito";

  public static final String MENSAJE_NOT_FOUND = ":: No existe %s para el ID: %s ingresado";

  public static final String MENSAJE_USUARIO_NO_ENCONTRADO = ":: Usuario o password incorrectos";

  public static final String RESPONSE_ERROR_500 = "Error interno del servidor. Por favor, intente nuevamente más tarde.";

  public static final String RESPONSE_ERROR_400 = "Solicitud inválida. Verifique los datos enviados e intente nuevamente.";

  public static final String RESPONSE_ERROR_404 = "Recurso no encontrado. Verifique los datos proporcionados.";

  public static final String RESPONSE_ERROR_409 = "Conflicto en la solicitud. El recurso ya existe o hay un conflicto con los datos.";

  public static final String RESPONSE_ERROR_401 = "No autorizado. Debe iniciar sesión o proporcionar credenciales válidas.";

  public static final String RESPONSE_ERROR_403 = "Acceso denegado. No tiene permisos para realizar esta acción.";

  public static final Boolean IND_INACTIVO = false;

  public static final Boolean IND_ACTIVO = true;

  public static final String STATUS_CREADO = "CREADO";

  public static final String CONFLICTO_REGISTRO = ":: El %s ya se encuentra registrado";

  public static final String MENSAJE_CAJA_CERRADA = "Se procedio con el cierre de la caja";

  public static final String MSJ_CAJA_EXISTE = "Ya existe una caja abierta para el usuario '%s' en la fecha '%s'";

  public static final String MONEDA_PER= "SOLES";

  public static final String MSJ_MOVIMIENTO_AGREGADO = "Movimiento agregado correctamente a la caja con ID '%s'";

  public static final String MSJ_CAJA_CERRADA = "La caja se encuentra cerrada";

  public static final String MSJ_CAJA_NO_ABIERTA = "No existe caja abierta en la fecha en curso";

  public static final BigDecimal IGV = new BigDecimal("0.18");

  public static final String BASE_URL_SERVICIO_EXTERNO = "https://api.apis.net.pe/";
  public static final String SERVICIO_EXTERNO_NAME_CLIENT = "apis-client";
  public static final String RENIEC_PATH = "/v2/reniec/dni";
  public static final String SUNAT_PATH = "/v2/sunat/ruc/full";
  public static final String MENSAJE_ERROR_DNI = ":: No se encontró información para el DNI: %s";
  public static final String MENSAJE_ERROR_SUNAT = ":: No se encontró información para el RUC: %s";
  public static final String CLAIM_USER = "username";
  public static final String CLAIM_ROL = "rol";
  public static final String CLAIM_NOMBRE_COMPLETO = "nombreCompleto";
  public static final String CLAIM_IS_USA_EMPLEADO = "isUsaEmpleado";
  public static final String CLAIM_SUCURSAL = "sucursal";


  public static final String REFRESH = "refreshToken";
  public static final String TYPE_TOKEN = "type";
  public static final String ACCESS = "accessToken";

  public static final String[] ENDPOINTS_PERMIT = {
          "/api/autenticacion/**",
          "/api/articulos/**",
          "/api/empresas/**"
  };

  public static final String[] ENDPOINTS_VENDEDOR = {
          "/api/articulos/**",
          "/api/ventas/**"
  };

  public static final String[] ENDPOINTS_ADMIN = {
          "/api/personas/**",
          "/api/empleados/**",
          "/api/usuarios/**",
          "/api/sucursales/**",
          "/api/empresas/**",
          "/api/series/**",
          "/api/articulos/**",

          "/api/inventario/**",
          "/api/categorias/**",
          "/api/marcas/**",
          "/api/unidadMedidas/**",
          "/api/kardex/**",

          "/api/clientes/**",
          "/api/ventas/**",

          "/api/proveedores/**",
          "/api/compras/**",

          "/api/caja/**",
          "/api/servicio/externo/**",
  };

  public static final String ROL_ADMIN = "ROLE_ADMIN";
  public static final String ROL_VENDEDOR = "ROLE_VENDEDOR";
  public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

  public static final String URL_BASE_CLIENT = "http://localhost:4200";
  public static final String[] ALLOWED_METHODS = {
          "GET", "POST", "PUT", "DELETE", "PATCH"
  };

}
