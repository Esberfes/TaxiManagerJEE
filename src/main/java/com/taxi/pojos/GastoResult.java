package com.taxi.pojos;

import java.math.BigDecimal;

public class GastoResult {
    
    private String concepto;
    private BigDecimal importe;

    public GastoResult(String concepto) {
        this.concepto = concepto;
        importe = new BigDecimal("0.00");
    }
    
    public void addGasto(Gasto gasto) {
        importe = importe.add(gasto.getImporte());
    }
    
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
}
