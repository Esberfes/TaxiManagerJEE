package com.taxi.pojos.out;

import com.taxi.pojos.Gasto;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResumenMes {
    private BigDecimal recaudacion;
    private BigDecimal salarios;
    private Map<String, BigDecimal> gastosOp;
    private Map<String, BigDecimal> gastosGes;
    private BigDecimal beneficioOp;
    private BigDecimal beneficio;
    private BigDecimal gastosOpTotal;
    private BigDecimal gastosGesTotal;

    public ResumenMes() {
        recaudacion = new BigDecimal("0.00");
        salarios = new BigDecimal("0.00");
        beneficioOp = new BigDecimal("0.00");
        beneficio = new BigDecimal("0.00");
        gastosOpTotal = new BigDecimal("0.00");
        gastosGesTotal = new BigDecimal("0.00");
        gastosOp = new LinkedHashMap<>();
        gastosGes = new LinkedHashMap<>();
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

    public Map<String, BigDecimal> getGastosOp() {
        return gastosOp;
    }

    public void setGastosOp(Map<String, BigDecimal> gastosOp) {
        this.gastosOp = gastosOp;
    }

    public Map<String, BigDecimal> getGastosGes() {
        return gastosGes;
    }

    public void setGastosGes(Map<String, BigDecimal> gastosGes) {
        this.gastosGes = gastosGes;
    }

    public BigDecimal getBeneficioOp() {
        return beneficioOp;
    }

    public void setBeneficioOp(BigDecimal beneficioOp) {
        this.beneficioOp = beneficioOp;
    }

    public BigDecimal getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(BigDecimal beneficio) {
        this.beneficio = beneficio;
    }

    public BigDecimal getGastosOpTotal() {
        return gastosOpTotal;
    }

    public void setGastosOpTotal(BigDecimal gastosOpTotal) {
        this.gastosOpTotal = gastosOpTotal;
    }

    public BigDecimal getGastosGesTotal() {
        return gastosGesTotal;
    }

    public void setGastosGesTotal(BigDecimal gastosGesTotal) {
        this.gastosGesTotal = gastosGesTotal;
    }
}
