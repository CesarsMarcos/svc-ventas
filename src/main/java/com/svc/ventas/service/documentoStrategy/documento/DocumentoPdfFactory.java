package com.svc.ventas.service.documentoStrategy.documento;

import com.svc.ventas.models.enums.TipoDocumento;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DocumentoPdfFactory {

  private final Map<TipoDocumento, DocumentoPdfStrategy> estrategias;

  public DocumentoPdfFactory(FacturaVentaPdf factura,
                               BoletaVentaPdf boleta,
                               TicketVentaPdf ticket) {
    this.estrategias = Map.of(
            TipoDocumento.FACTURA, factura,
            TipoDocumento.BOLETA, boleta,
            TipoDocumento.TICKET, ticket
    );
  }
  public DocumentoPdfStrategy obtener(TipoDocumento documento) {

    DocumentoPdfStrategy strategy = estrategias.get(documento);

    if (strategy == null) {
      throw new IllegalArgumentException(
              "No existe estrategia para: " + documento
      );
    }
    return estrategias.get(documento);
  }

}
