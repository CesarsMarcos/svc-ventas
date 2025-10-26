package com.svc.ventas.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDto;
import com.svc.ventas.models.mapstruct.mappers.TipoDocumentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.TipoDocumentoRepo;
import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.service.ITipoDocumentoService;
import com.svc.ventas.util.Constantes;

@Service
@RequiredArgsConstructor
public class TipoDocumentoImpl implements ITipoDocumentoService  {

	private final TipoDocumentoRepo tipoRepo;

	private final TipoDocumentoMapper tipoDocumentoMapper;

	@Override
	public List<TipoDocumento> lista() {return tipoRepo.findAll();}

	@Override
	public List<TipoDocumentoDto> listaPorTipo(Integer tipo) {
		return tipoRepo.findAll().stream()
						.filter(x-> Objects.equals(x.getTipo(), tipo))
						.map(tipoDocumentoMapper::mapToTipoDocumentoSelected)
						.collect(Collectors.toList());
	}

	@Override
	public Response agregar(TipoDocumentoDto tipoDocumento) {
		tipoRepo.save(tipoDocumentoMapper.mapTipoDocumento(tipoDocumento));
		return Response.builder()
						.mensaje(Constantes.MENSAJE_SAVE)
								.build();
	}

	@Override
	public Response modificar(int id, TipoDocumento sucursal) {
		TipoDocumento tipoDocSave = tipoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "TipoDocumento", id)));
		tipoDocSave.setDescripcion(sucursal.getDescripcion());
		tipoRepo.save(tipoDocSave);

		return Response.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public TipoDocumentoDto obtener(int id) {
		return tipoRepo.findById(id)
				.map(tipoDocumentoMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "TipoDocumento", id)));
	}

	@Override
	public void eliminar(int id) {
		TipoDocumento tipoDocSave = tipoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "TipoDocumento", id)));
		tipoDocSave.setIndEstado(Constantes.IND_INACTIVO);
		tipoRepo.save(tipoDocSave);

	}

}
