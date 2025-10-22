package com.svc.ventas.util;

public class Constantes {

  public static final String MENSAJE_SAVE = ":: Registro guardado con éxito";

  public static final String MENSAJE_MOD = ":: Registro modificado con éxito";

  public static final String MENSAJE_NOT_FOUND = ":: No existe %s para el ID: %s ingresado";

  public static final String MENSAJE_NOT_FOUND_CAJA = ":: No existe %s abierta para el día en curso";

  public static final Boolean IND_INACTIVO = false;

  public static final Boolean IND_ACTIVO = true;

  public static final String STATUS_CREADO = "CREADO";

  public static final String CONFLICTO_REGISTRO = ":: El %s ya se encuentra registrado";

  public static final String CAJA_ABIERTA = "ABIERTO";

  public static final String CAJA_CERRADA = "CERRADO";

  public static final String MENSAJE_CAJA_CERRADA = "Se procedio con el cierre de la caja";

  //CAJA
  public static final String MSJ_CAJA_EXISTE = "Ya existe una caja abierta para el usuario '%s' en la fecha '%s'";

  public static final String MSJ_MOVIMIENTO_AGREGADO = "Movimiento agregado correctamente a la caja con ID '%s'";

  public static final String MSJ_CAJA_CERRADA = "La caja se encuentra cerrada";

  public static final String BASE_URL_SERVICIO_EXTERNO = "https://api.apis.net.pe/";
  public static final String SERVICIO_EXTERNO_NAME_CLIENT = "apis-client";
  public static final String RENIEC_PATH = "/v2/reniec/dni";
  public static final String SUNAT_PATH = "/v2/sunat/ruc/full";
  public static final String MENSAJE_ERROR_DNI = ":: No se encontró información para el DNI: %s";
  public static final String MENSAJE_ERROR_SUNAT = ":: No se encontró información para el RUC: %s";
  public static final String CLAIM_USER = "username";
  public static final String CLAIM_ROL = "rol";
  public static final String REFRESH = "refreshToken";
  public static final String TYPE_TOKEN = "type";
  public static final String ACCESS = "accessToken";
  public static final String[] ENDPOINTS_PERMIT = {
          "/api/autenticacion/**",
          "/api/articulos/**",
          "/api/empresas/**"
  };

  public static final String[] ENDPOINTS_USER = {
          "/api/categorias/**",
          "/api/marcas/**",
          "/api/articulos/**",
          "/api/unidadMedidas/**",
          "/api/compras/**",
          "/api/proveedores/**",
          "/api/empresas/**",
          "/api/tipoPersonas/**",
          "/api/tipoDocumentos/**",
          "/api/series/**",
          "/api/charts/**",
          "/api/articulos/**",
          "/api/proveedores/**",
          "/api/series/**"
  };

  public static final String[] ENDPOINTS_ADMIN = {
          "/api/servicio/externo/**",
          "/api/global/**",
          "/api/sucursales/**",
          "/api/personas/**",
          "/api/caja/**"
  };

  public static final String ROL_ADMIN = "ROLE_ADMIN";
  public static final String ROL_USER = "ROLE_USER";

  public static final String URL_BASE_CLIENT = "http://localhost:4200";
  public static final String[] ALLOWED_METHODS = {
          "GET", "POST", "PUT", "DELETE"
  };


}
