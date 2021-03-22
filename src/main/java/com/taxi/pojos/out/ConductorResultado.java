package com.taxi.pojos.out;

import com.taxi.pojos.Conductor;

import java.math.BigDecimal;

public class ConductorResultado {

    private Conductor conductor;
    private BigDecimal recaudacion;
    private BigDecimal liquido;
    private BigDecimal tarjeta;
    private BigDecimal app;
    private BigDecimal efectivo;
    private BigDecimal salario;

    public ConductorResultado(Conductor conductor, BigDecimal recaudacion, BigDecimal liquido, BigDecimal tarjeta,
                              BigDecimal app, BigDecimal efectivo, BigDecimal salario) {
        this.conductor = conductor;
        this.recaudacion = recaudacion == null ? new BigDecimal("0.00") : recaudacion ;
        this.liquido = liquido  == null ? new BigDecimal("0.00") : liquido;
        this.tarjeta = tarjeta == null ? new BigDecimal("0.00") :tarjeta;
        this.app = app  == null ? new BigDecimal("0.00") : app;
        this.efectivo = efectivo  == null ? new BigDecimal("0.00") : efectivo;
        this.salario = salario  == null ? new BigDecimal("0.00") : salario;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public BigDecimal getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(BigDecimal recaudacion) {
        this.recaudacion = recaudacion;
    }

    public BigDecimal getLiquido() {
        return liquido;
    }

    public void setLiquido(BigDecimal liquido) {
        this.liquido = liquido;
    }

    public BigDecimal getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(BigDecimal tarjeta) {
        this.tarjeta = tarjeta;
    }

    public BigDecimal getApp() {
        return app;
    }

    public void setApp(BigDecimal app) {
        this.app = app;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
