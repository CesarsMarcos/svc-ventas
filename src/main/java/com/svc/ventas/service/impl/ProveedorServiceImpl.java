package com.svc.ventas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import com.svc.ventas.models.specifications.ProductStockSpecifications;
import com.svc.ventas.models.specifications.ProveedorSpecifications;
import com.svc.ventas.service.IServicioExterno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.models.dao.ProveedorRepo;
import com.svc.ventas.models.entity.Proveedor;
import com.svc.ventas.models.mapstruct.dto.ProveedorDto;
import com.svc.ventas.models.mapstruct.mappers.ProveedorMapper;
import com.svc.ventas.service.IProveedorService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements IProveedorService {

	private final  ProveedorRepo proveedorRepo;
	
	private final ProveedorMapper proveedorMapper;

	private final IServicioExterno iServicioExterno;

	@Override
	public Map<String, Object> searchProveedor(String razonSocial, Pageable pageable) {

		Specification<Proveedor> spec =  Specification.where(null);

		if(Objects.nonNull(razonSocial) && !razonSocial.isEmpty()) {
			spec = spec.and(ProveedorSpecifications.hasRazonSocial(razonSocial));
		}

		Page<Proveedor> proveedorPage = proveedorRepo.findAll(spec, pageable);

		List<ProveedorDto> proveedoresDto = proveedorPage.getContent()
						.stream()
						.map(proveedorMapper::mapToProveedorDto)
						.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("proveedores", proveedoresDto);
		response.put("currentPage", proveedorPage.getNumber());
		response.put("totalItems", proveedorPage.getTotalElements());
		response.put("totalPages", proveedorPage.getTotalPages());

		return response;
	}

	@Override
	public List<ProveedorDto> proveedores() {
		return proveedorRepo.findAll()
						.stream()
						.map(proveedorMapper::mapToProveedorDto)
						.collect(Collectors.toList());
	}

	public List<ProveedorSelectedDto> proveedoresListSelected() {
		return proveedorRepo.findAll()
				.stream().map(proveedorMapper::mapToProveedorSelected)
				.collect(Collectors.toList());
	}

	@Override
	public Response registrar(ProveedorDto proveedorDto) {
		proveedorRepo.save(proveedorMapper.mapToProveedor(proveedorDto));
		return Response.builder().mensaje(Constantes.MENSAJE_SAVE).build();
	}

	@Override
	public Response modificar(Long id, ProveedorDto proveedorDto) {
		proveedorRepo.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Proveedor", id)));
		return Response.builder().mensaje(Constantes.MENSAJE_MOD).build();
	}

	@Override
	public ProveedorDto obtener(Long id) {
		return proveedorRepo.findById(id)
				.map(proveedorMapper::mapToProveedorDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Proveedor", id)));
	}

	@Override
	public void eliminar(Long id) {
		Proveedor proveedorSave = proveedorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Proveedor", id)));
		proveedorSave.setIndEstado(Constantes.IND_ACTIVO);
		proveedorRepo.save(proveedorSave);
	}

	@Override
	public Object searchProveedor(String tipoDocuento, String numDocumento) {
		validarLongtudDocumento(tipoDocuento, numDocumento);
		if("DNI".equalsIgnoreCase(tipoDocuento)) {
			return iServicioExterno.getInfoReniec(numDocumento);
		} else {
			return iServicioExterno.getInfoSunat(numDocumento);
		}
	}

	private void validarLongtudDocumento(String tipoDocumento, String numDocumento) {
		int longitud = numDocumento.trim().length();
		if (Objects.isNull(numDocumento) || numDocumento.isBlank()) {
			throw new BusinessException("El número de documento es obligatorio");
		}
		switch (tipoDocumento.toUpperCase()) {
			case "DNI" -> {
				if (longitud != 8) {
					throw new BusinessException("El DNI debe tener 8 dígitos");
				}
			}
			case "RUC" -> {
				if (longitud != 11) {
					throw new BusinessException("El RUC debe tener 11 dígitos");
				}
			}
			default -> throw new BusinessException("Tipo de documento no válido");
		}
	}

}
