package com.svc.ventas.service.documentoStrategy.documento;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;
import com.svc.ventas.models.mapstruct.dto.VentaDetailDto;
import com.svc.ventas.service.documentoStrategy.BasePdfGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Log4j2
@Component
public class TicketVentaPdf extends BasePdfGenerator
        implements DocumentoPdfStrategy {

  @Override
  public byte[] generar(DetalleImpresionDto venta) {
    log.info("Generando documento pdf para VENTA que solicito TICKET");

    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      PdfWriter writer = new PdfWriter(out);
      PdfDocument pdf = new PdfDocument(writer);
      PageSize ticket = new PageSize(226, 600); // ancho ~80mm
      Document doc = new Document(pdf, ticket);
      doc.setMargins(5, 5, 5, 5);

      doc.add(new Paragraph("MI TIENDA")
              .setTextAlignment(TextAlignment.CENTER)
              .setBold());

      doc.add(new Paragraph("RUC: 12345678901")
              .setTextAlignment(TextAlignment.CENTER));

      doc.add(new Paragraph("-----------------------"));

      venta.getProductos().forEach(d -> {
        doc.add(new Paragraph(
                d.getNombre() +
                        " x" + d.getCantidad() +
                        "  S/" + d.getSubTotal() //debe ser total
        ));
      });

      doc.add(new Paragraph("-----------------------"));

      doc.add(new Paragraph("TOTAL: S/ " + venta.getTotal())
              .setBold());

      doc.add(new Paragraph("Gracias por su compra")
              .setTextAlignment(TextAlignment.CENTER));

      doc.close();

      return out.toByteArray();


    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
