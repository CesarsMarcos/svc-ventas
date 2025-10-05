package com.svc.ventas.service.impl;

import com.svc.ventas.models.dao.UbigeoRepository;
import com.svc.ventas.service.UbigeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UbigeoServiceImpl implements UbigeoService {

    private final UbigeoRepository ubigeoRepo;

    @Override
    public List<String> getDepartamentos() {
        return ubigeoRepo.findDistinctDepartamentos();
    }

    @Override
    public List<Map<String, String>> provinciasByDepartamento(String departamento) {
        return ubigeoRepo.findProvinciasByDepartamento(departamento);
    }

    @Override
    public List<Map<String, String>> distritosByProvincia(String provincia) {
        return ubigeoRepo.findDistritosByProvincia(provincia);
    }
}
