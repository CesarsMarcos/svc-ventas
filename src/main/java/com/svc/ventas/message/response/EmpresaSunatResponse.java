package com.svc.ventas.message.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaSunatResponse {
    public String razonSocial;
    public String tipoDocumento;
    public String numeroDocumento;
    public String estado;
    public String condicion;
    public String direccion;
    public String ubigeo;
    public String viaTipo;
    public String viaNombre;
    public String zonaCodigo;
    public String zonaTipo;
    public String numero;
    public String interior;
    public String lote;
    public String dpto;
    public String manzana;
    public String kilometro;
    public String distrito;
    public String provincia;
    public String departamento;
    @JsonProperty("EsAgenteRetencion")
    public boolean esAgenteRetencion;
    @JsonProperty("EsBuenContribuyente")
    public boolean esBuenContribuyente;
    public Object localesAnexos;
    public String tipo;
    public String actividadEconomica;
    public String numeroTrabajadores;
    public String tipoFacturacion;
    public String tipoContabilidad;
    public String comercioExterior;
}
