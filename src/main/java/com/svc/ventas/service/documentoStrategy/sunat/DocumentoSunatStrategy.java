package com.svc.ventas.service.documentoStrategy.sunat;

public interface DocumentoSunatStrategy<T> {

  String generarXml(T data);

  void enviarSunat(String xml);

}
