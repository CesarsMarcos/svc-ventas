package com.svc.ventas.exception;

public class BusinessException extends RuntimeException {

  public BusinessException(String mensaje) {
    super(mensaje);
  }

}
