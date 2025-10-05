package com.svc.ventas.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private JsonUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> T fromJsonFile(String path, Class<T> clazz) {
    try (InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(path)) {
      return objectMapper.readValue(inputStream, clazz);
    } catch (Exception e) {
      throw new RuntimeException("Error al leer JSON desde archivo: " + path, e);
    }
  }
}