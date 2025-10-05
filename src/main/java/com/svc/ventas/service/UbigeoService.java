package com.svc.ventas.service;

import java.util.List;
import java.util.Map;

public interface UbigeoService {

    List<String> getDepartamentos();

    List<Map<String, String>> provinciasByDepartamento(String departamento);

    List<Map<String, String>> distritosByProvincia(String provincia);

}
