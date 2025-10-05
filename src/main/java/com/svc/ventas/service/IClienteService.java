package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.models.mapstruct.dto.ClienteGetDto;
import com.svc.ventas.models.mapstruct.dto.ClientePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Cliente;
import org.springframework.transaction.annotation.Transactional;

public interface IClienteService {

	List<ClienteGetDto> clientes();

	@Transactional
	Response agregar(ClientePostDto cliente);

	@Transactional
	Response modificar(Integer id, ClientePostDto cliente);

	ClienteGetDto obtener(Integer id);

	Page<Cliente> searchCliente(String nombre, String documento, Pageable pageable);

}
