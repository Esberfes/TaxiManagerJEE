package pojos;

import java.math.BigDecimal;

public class RecaudacionResult {
    private Integer id;
    private BigDecimal recaudacion;

    public RecaudacionResult(Integer id) {
        this.id = id;
        this.recaudacion =  new BigDecimal("0.00");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(BigDecimal recaudacion) {
        this.recaudacion = recaudacion;
    }

    public void addRecaudacion(RecaudacionIngreso ingreso) {
        recaudacion = this.recaudacion.add(ingreso.getRecaudacion());
    }
}
