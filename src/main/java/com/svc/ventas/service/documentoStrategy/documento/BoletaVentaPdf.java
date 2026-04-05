package com.svc.ventas.service.documentoStrategy.documento;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;
import com.svc.ventas.models.mapstruct.dto.VentaDetailDto;
import com.svc.ventas.service.documentoStrategy.BasePdfGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class BoletaVentaPdf extends BasePdfGenerator
        implements DocumentoPdfStrategy {

  @Override
  public byte[] generar(DetalleImpresionDto venta) {
    log.info("Generando documento pdf para VENTA que solicito BOLETA");

    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      PdfWriter writer = new PdfWriter(out);
      PdfDocument pdf = new PdfDocument(writer);

      Document doc = crearDocumento(pdf);

      empresa(doc, venta);

      comprobante(doc, venta);

      cliente(doc, venta);

      detalle(doc, venta);

      totales(doc, venta);

      tipoPago(doc, venta);

      totalpagarATexto(doc, venta);

      footer(doc);

      qr(doc, pdf, venta.toString());

      doc.close();

      return out.toByteArray();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
