package com.svc.ventas.service.impl;

import java.util.List;

import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaDto;
import com.svc.ventas.models.mapstruct.mappers.Empresamapper;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.GlobalRepository;
import com.svc.ventas.service.IEmpresaService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements IEmpresaService {

	private final GlobalRepository globalRepo;

	private final Empresamapper globalMapper;

	@Override
	public List<Empresa> listar() {
		return globalRepo.findAll();
	}

	@Override
	public EmpresaDto obtener(Integer id) {
		return globalRepo.findById(id)
				.map(globalMapper::mapToGetDto)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empresa", id)));

	}

	@Override
	public Response guardar(EmpresaDto empresaDto) {
		globalRepo.save(globalMapper.mapToEntity(empresaDto));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Integer id, EmpresaDto empresa) {
		 globalRepo.findById(id)
				.map(global -> {
					global.setRuc(empresa.getRuc());
					global.setRazonSocial(empresa.getRazonSocial());
					global.setNombreComercial(empresa.getNombreComercial());
					global.setDireccion(empresa.getDireccion());
					global.setEmail(empresa.getEmail());
					global.setTelefono(empresa.getTelefono());
					global.setUbigeo(empresa.getUbigeo());
					global.setSimboloMoneda(empresa.getSimboloMoneda());
					global.setNombreImpuesto(empresa.getNombreImpuesto());
					global.setPorcentajeImpuesto(empresa.getPorcentajeImpuesto());
					global.setLogo(empresa.getLogo());
					return globalRepo.save(global);
				}).
				orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Global", id)));

		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_MOD)
				.build();
	}

	@Override
	public void eliminar(Integer id) {
		Empresa empresaSave = globalRepo.findById(id)
						.orElseThrow(()-> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND,"Global",id)));
		empresaSave.setIndEstado(Constantes.IND_INACTIVO);
		globalRepo.save(empresaSave);
	}

}
