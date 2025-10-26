package com.svc.ventas.service;

import java.util.List;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDto;
import org.springframework.transaction.annotation.Transactional;

public interface ITipoDocumentoService {

	List<TipoDocumento> lista ();

	List<TipoDocumentoDto> listaPorTipo (Integer tipo);

	@Transactional
	Response agregar(TipoDocumentoDto sucursal);

	@Transactional
	Response modificar(int id, TipoDocumento sucursal);

	TipoDocumentoDto obtener (int id);
	
	void eliminar(int id);
	
	
}
