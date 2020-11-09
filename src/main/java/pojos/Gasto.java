package pojos;

import entities.GastosEntity;

import java.math.BigDecimal;
import java.util.Date;

public class Gasto {

    private Integer id;
    private Licencia licencia;
    private FormasPago formaPago;
    private ConceptosGastos concepto;
    private BigDecimal importe;
    private Date creado;
    private Date actualizado;

    public Gasto() {
    }

    public Gasto(GastosEntity gasto) {
        this.id = gasto.getId();
        this.licencia = new Licencia(gasto.getLicenciasEntity());
        this.formaPago = new FormasPago(gasto.getFormasPagosGastosEntity());
        this.concepto = new ConceptosGastos(gasto.getConceptosGastosEntity());
        this.importe = gasto.getImporte();
        this.actualizado = gasto.getActualizado();
        this.creado = gasto.getCreado();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public ConceptosGastos getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptosGastos concepto) {
        this.concepto = concepto;
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
}
