package com.taxi.pojos;

import com.taxi.entities.GastosEntity;

import java.math.BigDecimal;
import java.util.Date;

public class Gasto {

    private Long id;
    private Licencia licencia;
    private FormasPago formaPago;
    private TiposGasto tipoGasto;
    private BigDecimal importe;
    private String definicion;
    private Date fechaFactura;
    private Date creado;
    private Date actualizado;

    public Gasto() {
    }

    public Gasto(GastosEntity gasto) {
        this.id = gasto.getId();
        this.licencia = gasto.getLicenciasEntity() != null ? new Licencia(gasto.getLicenciasEntity()) : new EmptyLicencia();
        this.formaPago = new FormasPago(gasto.getFormasPagosGastosEntity());
        this.tipoGasto = gasto.getTiposGastosEntity() != null ? new TiposGasto(gasto.getTiposGastosEntity()) : null;
        this.importe = gasto.getImporte();
        this.fechaFactura = gasto.getFechaFactura();
        this.definicion = gasto.getDefinicion();
        this.actualizado = gasto.getActualizado();
        this.creado = gasto.getCreado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }

    public FormasPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormasPago formaPago) {
        this.formaPago = formaPago;
    }


    public TiposGasto getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(TiposGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
}
