package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.request.ClienteCreateParaVentaRequest;
import com.svc.ventas.message.request.ClienteCreateRequest;
import com.svc.ventas.message.response.ClienteSaveResponse;
import com.svc.ventas.message.response.ResponseData;
import com.svc.ventas.models.mapstruct.dto.ClienteDto;
import com.svc.ventas.models.mapstruct.dto.ClienteSelectedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Cliente;
import org.springframework.transaction.annotation.Transactional;

public interface IClienteService {

	List<ClienteDto> clientes();

	List<ClienteSelectedDto> clientesListSelected();

	@Transactional
	Response agregar(ClienteCreateRequest clienterRequest);

	@Transactional
	ResponseData<ClienteSaveResponse> agregarParaVenta(ClienteCreateParaVentaRequest clienterRequest);

	@Transactional
	Response modificar(Integer id, ClienteDto cliente);

	ClienteDto obtener(Integer id);

	Page<Cliente> searchCliente(String nombre, String documento, Pageable pageable);

}
