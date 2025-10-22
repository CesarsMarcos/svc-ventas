package com.svc.ventas.service;

import java.util.List;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.UnidadMedida;
import com.svc.ventas.models.mapstruct.dto.UnidadMedidaDto;
import org.springframework.transaction.annotation.Transactional;

public interface IUnidadMedidaService {

    List<UnidadMedida> unidades();

    @Transactional
    Response guardar(UnidadMedida unidad);

    @Transactional
    Response modificar(int id, UnidadMedida unidad);

    UnidadMedidaDto obtener(int id);

    void eliminar(int id);

}
