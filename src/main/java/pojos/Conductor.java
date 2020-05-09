package pojos;

import entities.ConductorEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Conductor implements Serializable {

    private Long id;
    private String nombre;
    private Empresa empresa;
    private BigDecimal complemento_iva;
    private BigDecimal t065;
    private BigDecimal t060;
    private BigDecimal t055;
    private BigDecimal t050;
    private Date creado;
    private Date actualizado;

    public Conductor() {
    }

    public Conductor(ConductorEntity conductorEntity) {
        this.id = conductorEntity.getId();
        this.nombre = conductorEntity.getNombre();
        this.empresa = new Empresa(conductorEntity.getEmpresasEntity());
        this.complemento_iva = conductorEntity.getComplementoIva();
        this.t065 = conductorEntity.getT065();
        this.t060 = conductorEntity.getT060();
        this.t055 = conductorEntity.getT055();
        this.t050 = conductorEntity.getT050();
        this.creado = conductorEntity.getCreado();
        this.actualizado = conductorEntity.getActualizado();
    }

    public Conductor(String nombre, Empresa empresa, BigDecimal complemento_iva, BigDecimal t065, BigDecimal t060, BigDecimal t055, BigDecimal t050) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.complemento_iva = complemento_iva;
        this.t065 = t065;
        this.t060 = t060;
        this.t055 = t055;
        this.t050 = t050;
    }

    public Conductor(String name) {
        this.nombre = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, empresa, complemento_iva, t065, t060, t055, t050, creado, actualizado);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conductor that = (Conductor) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(empresa, that.empresa) &&
                Objects.equals(complemento_iva, that.complemento_iva) &&
                Objects.equals(t065, that.t065) &&
                Objects.equals(t060, that.t060) &&
                Objects.equals(t055, that.t055) &&
                Objects.equals(t050, that.t050) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getComplemento_iva() {
        return complemento_iva;
    }

    public void setComplemento_iva(BigDecimal complemento_iva) {
        this.complemento_iva = complemento_iva;
    }

    public BigDecimal getT065() {
        return t065;
    }

    public void setT065(BigDecimal t065) {
        this.t065 = t065;
    }

    public BigDecimal getT060() {
        return t060;
    }

    public void setT060(BigDecimal t060) {
        this.t060 = t060;
    }

    public BigDecimal getT055() {
        return t055;
    }

    public void setT055(BigDecimal t055) {
        this.t055 = t055;
    }

    public BigDecimal getT050() {
        return t050;
    }

    public void setT050(BigDecimal t050) {
        this.t050 = t050;
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
