package com.taxi.pojos;

import java.math.BigDecimal;

public class SalariosResult {

    private long id;
    private BigDecimal salario;
    private String nombre;

    public SalariosResult(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.salario = new BigDecimal("0.00");
    }

    public void addSalario(BigDecimal salario) {
       this.salario = this.salario.add(salario);
    }

    public long getId() {
        return id;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getNombre() {
        return nombre;
    }
}
