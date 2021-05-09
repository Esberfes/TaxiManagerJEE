package com.taxi.pojos.out;

import java.math.BigDecimal;

public class ResumenMes {
    private BigDecimal recaudacion;
    private BigDecimal salarios;
    private BigDecimal gastosOp;
    private BigDecimal beneficioOp;

    public ResumenMes() {
        recaudacion = new BigDecimal("0.00");
        salarios = new BigDecimal("0.00");
        gastosOp = new BigDecimal("0.00");
        beneficioOp = new BigDecimal("0.00");
    }

    public ResumenMes(BigDecimal recaudacion, BigDecimal salarios, BigDecimal gastosOp, BigDecimal beneficioOp) {
        this.recaudacion = recaudacion;
        this.salarios = salarios;
        this.gastosOp = gastosOp;
        this.beneficioOp = beneficioOp;
    }

    public BigDecimal getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(BigDecimal recaudacion) {
        this.recaudacion = recaudacion;
    }

    public BigDecimal getSalarios() {
        return salarios;
    }

    public void setSalarios(BigDecimal salarios) {
        this.salarios = salarios;
    }

    public BigDecimal getGastosOp() {
        return gastosOp;
    }

    public void setGastosOp(BigDecimal gastosOp) {
        this.gastosOp = gastosOp;
    }

    public BigDecimal getBeneficioOp() {
        return beneficioOp;
    }

    public void setBeneficioOp(BigDecimal beneficioOp) {
        this.beneficioOp = beneficioOp;
    }
}
