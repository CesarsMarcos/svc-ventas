package com.svc.ventas.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private JsonUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> T fromJsonFile(String path, Class<T> clazz) {
    InputStream inputStream = null;
    try {
      inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(path);

      if (inputStream == null) {
        throw new IllegalArgumentException(
                "No se encontró el archivo JSON en el classpath: " + path
        );
      }

      return objectMapper.readValue(inputStream, clazz);

    } catch (Exception e) {
      throw new RuntimeException(
              "Error al leer o parsear el JSON desde archivo: " + path + " - " + e.getMessage(),
              e
      );
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (Exception ignored) {}
      }
    }
  }
}