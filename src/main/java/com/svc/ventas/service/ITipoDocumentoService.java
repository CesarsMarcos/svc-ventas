package com.svc.ventas.service;

import java.util.List;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDto;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoGetDto;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoSelectedDto;
import org.springframework.transaction.annotation.Transactional;

public interface ITipoDocumentoService {

	List<TipoDocumento> lista ();

	List<TipoDocumentoSelectedDto> listaPorTipo (Integer tipo);

	@Transactional
	Response agregar(TipoDocumentoDto sucursal);

	@Transactional
	Response modificar(int id, TipoDocumento sucursal);

	TipoDocumentoGetDto obtener (int id);
	
	void eliminar(int id);
	
	
}
