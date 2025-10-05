package com.svc.ventas.service.impl;

import java.util.List;

import com.svc.ventas.models.mapstruct.dto.UnidadMedidaGetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.UnidadMedidaRepo;
import com.svc.ventas.models.entity.UnidadMedida;
import com.svc.ventas.service.IUnidadMedidaService;
import com.svc.ventas.util.Constantes;

@Service
public class UnidadMedidaServiceImpl implements IUnidadMedidaService {

	@Autowired
	private UnidadMedidaRepo unidadRepositorio;

	public List<UnidadMedida> unidades() {
		return unidadRepositorio.unidades();
	}

	public Response guardar(UnidadMedida unidad) {
		unidad.setIndEstado(Constantes.IND_ACTIVO);
		unidadRepositorio.save(unidad);
		return Response.builder()
						.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(int id, UnidadMedida unidad) {
		UnidadMedida uMedidaSave = unidadRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "UnidadMedida", id)));

		uMedidaSave.setNombre(unidad.getNombre());
		uMedidaSave.setPrefijo(unidad.getPrefijo());
		unidadRepositorio.save(uMedidaSave);

		return Response.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	public UnidadMedidaGetDto obtener(int id) {
		return unidadRepositorio.findById(id)
				.map(unidadMedida -> UnidadMedidaGetDto
                        .builder()
                        .prefijo(unidadMedida.getPrefijo())
                        .build())
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "UnidadMedida", id)));
	}

	@Override
	public void eliminar(int id) {
		UnidadMedida uMedidaSave = unidadRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "UnidadMedida", id)));
		uMedidaSave.setIndEstado(Constantes.IND_INACTIVO);
		unidadRepositorio.save(uMedidaSave);
	}

}
