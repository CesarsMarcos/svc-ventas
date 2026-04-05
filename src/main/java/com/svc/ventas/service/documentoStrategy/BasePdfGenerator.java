package com.svc.ventas.service.documentoStrategy;


import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.*;
import com.itextpdf.pdfa.PdfADocument;
import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;

import java.math.BigDecimal;
import java.net.MalformedURLException;

public class BasePdfGenerator {

  protected static final float WIDTH = 226f;
  protected static final float HEIGHT = 680f;

  protected static final float MARGIN_LEFT = 20f;
  protected static final float MARGIN_RIGHT = 28f;

  protected static final float ANCHO_UTIL = WIDTH - MARGIN_LEFT - MARGIN_RIGHT;

  protected Document crearDocumento(PdfDocument pdf) throws Exception {

    PageSize pageSize = new PageSize(WIDTH, HEIGHT);

    Document doc = new Document(pdf, pageSize);
    doc.setMargins(10, MARGIN_RIGHT, 10, MARGIN_LEFT);

    PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER);

    doc.setFont(font);
    doc.setFontSize(8);
    doc.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, 1));

    return doc;
  }

  protected void empresa(Document doc, DetalleImpresionDto venta) throws MalformedURLException {

    if (venta.getLogo() != null) {
      Image logo = new Image(ImageDataFactory.create("src/main/resources/images/logo.png"))
              .setWidth(80)
              .setHorizontalAlignment(HorizontalAlignment.CENTER);

      doc.add(logo);
    }

    doc.add(center(venta.getRazonSocial()).setFontSize(15).setBold());
    doc.add(center("RUC: " + venta.getRuc()));
    doc.add(center(venta.getDireccion()));
    doc.add(center(venta.getDistrito() + " - " + venta.getProvincia()));
  }

  protected void comprobante(Document doc, DetalleImpresionDto b) {

    doc.add(space());
    doc.add(space());

    doc.add(center(b.getTipoDocumento()).setBold());
    doc.add(center(b.getSerieCorrelativo()));

    doc.add(space());
    doc.add(space());

    doc.add(left("FECHA DE EMISION: " + b.getFecha()));
    doc.add(left("CAJERO: " + b.getUsuarioRegistro().toUpperCase()));
  }

  protected void cliente(Document doc, DetalleImpresionDto b) {

    doc.add(left("CLIENTE: " + b.getNomCliente()));
    doc.add(left("DOCUMENTO: " + b.getNumDocumento()));
    doc.add(space());
    doc.add(linea());
  }

  protected String formatearProducto(String nombre, int cant, BigDecimal precio, BigDecimal total) {

    nombre = cortar(nombre, 28);

    String linea1 = String.format("%-28s", nombre);

    String linea2 = String.format(
            "%-10s %8s",
            cant + " x " + String.format("%.2f", precio),
            String.format("%.2f", total)
    );

    return linea1 + "\n" + linea2;
  }

  protected void detalle(Document doc, DetalleImpresionDto b) {

    b.getProductos().forEach(p -> {

      Table t1 = new Table(new float[]{1});
      t1.setWidth(UnitValue.createPointValue(178));
      t1.setBorder(Border.NO_BORDER);

      t1.addCell(new Cell()
              .add(new Paragraph(cortar(p.getNombre(), 30)))
              .setBorder(Border.NO_BORDER)
              .setPadding(0));

      doc.add(t1);

      Table t2 = new Table(new float[]{2, 1});
      t2.setWidth(UnitValue.createPointValue(178));
      t2.setBorder(Border.NO_BORDER);

      t2.addCell(new Cell()
              .add(new Paragraph(p.getCantidad() + " x " + p.getPrecioVenta()))
              .setBorder(Border.NO_BORDER)
              .setPadding(0));

      t2.addCell(new Cell()
              .add(new Paragraph(p.getSubTotal().toString()))
              .setTextAlignment(TextAlignment.RIGHT)
              .setBorder(Border.NO_BORDER)
              .setPadding(0));

      doc.add(t2);
    });

    doc.add(linea());
  }

  protected void totales(Document doc, DetalleImpresionDto venta) {

    doc.add(new Paragraph(right("NUMERO DE ITEMS:", BigDecimal.valueOf(venta.getProductos().size()))).setMargin(1));
    doc.add(new Paragraph(right("SUBTOTAL:", venta.getSubTotal())).setMargin(1));
    doc.add(new Paragraph(right("IGV   18%:", venta.getIgv())).setMargin(1));
    doc.add(space());
    doc.add(new Paragraph(right("TOTAL A PAGAR:", venta.getTotal())).setBold());
  }

  protected void tipoPago(Document document, DetalleImpresionDto venta) {
    document.add(space());
    document.add(left("PAGO: " + venta.getTipoPago())).setBold();
    document.add(space());
  }

  protected void totalpagarATexto(Document document, DetalleImpresionDto venta) {
    document.add(left("Son: " + venta.getTotalTexto())).setBold();
  }

  protected void qr(Document doc, PdfDocument pdf, String data) {

    BarcodeQRCode qr = new BarcodeQRCode(data);

    Image qrImg = new Image(qr.createFormXObject(pdf))
            .setWidth(100)
            .setHorizontalAlignment(HorizontalAlignment.CENTER);

    doc.add(space());
    doc.add(qrImg);
  }

  protected void footer(Document doc) {

    doc.add(space());
    doc.add(space());
    doc.add(space());
    doc.add(center("GRACIAS POR SU COMPRA").setBold());
    doc.add(center("VUELVA PRONTO"));
  }

  protected Paragraph center(String text) {
    return new Paragraph(text)
            .setTextAlignment(TextAlignment.CENTER)
            .setMargin(0);
  }

  protected String right(String label, BigDecimal value) {
    return String.format("%-22s %13.2f", label, value);
  }

  protected Paragraph left(String text) {
    return new Paragraph(text)
            .setTextAlignment(TextAlignment.LEFT)
            .setMargin(0);
  }

  protected Paragraph linea() {
    return new Paragraph("-------------------------------------")
            .setTextAlignment(TextAlignment.CENTER)
            .setMargin(0);
  }

  protected Paragraph space() {
    return new Paragraph(" ").setMargin(2);
  }

  protected String cortar(String text, int max) {
    return text.length() > max ? text.substring(0, max) : text;
  }

  private String rightFull(String label, double value) {
    return String.format("%-22s %10.2f", label, value);
  }

}
