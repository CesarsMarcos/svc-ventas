package com.svc.ventas.service.impl;

import java.util.List;

import com.svc.ventas.models.mapstruct.dto.TipoPersonaDto;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.TipoPersonaRepository;
import com.svc.ventas.models.entity.TipoPersona;
import com.svc.ventas.service.ITIpoPersonaService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoPersonaImpl implements ITIpoPersonaService {

	private final TipoPersonaRepository tipoRepo;

	@Override
	public List<TipoPersona> lista() {
		return tipoRepo.tiposSinPersona();
	}

	@Override
	public Response agregar(TipoPersona tipo) {
		tipo.setIndEstado(Constantes.IND_ACTIVO);
		tipoRepo.save(tipo);
		return Response.builder()
						.mensaje(Constantes.MENSAJE_SAVE)
								.build();
	}

	@Override
	public Response modificar(int id, TipoPersona tipo) {
		TipoPersona tipoSave = tipoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "TipoDocumento", id)));

		tipoSave.setDescripcion(tipo.getDescripcion());

		return Response.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public TipoPersonaDto obtener(int id) {
		return tipoRepo.findById(id)
				.map(tipo -> TipoPersonaDto.builder()
                        .idTipoPersona(tipo.getIdTipoPersona())
                        .build())
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "TipoDocumento", id)));
	}

	@Override
	public void eliminar(int id) {
		TipoPersona tipoSave = tipoRepo.findById(id)
				.orElseThrow(()-> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND,"TipoDocumento",id)));
		tipoSave.setIndEstado(Constantes.IND_ACTIVO);
		tipoRepo.save(tipoSave);

	}

}
