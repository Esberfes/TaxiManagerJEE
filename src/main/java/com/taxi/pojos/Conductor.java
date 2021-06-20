package com.taxi.pojos;

import com.taxi.entities.ConductorEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.taxi.utils.BigDecimalUtils.ensureNotNull;

public class Conductor implements Serializable {

    private Long id;
    private String nombre;
    private Empresa empresa;
    private BigDecimal complemento_iva;
    private BigDecimal t000;
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
        this.complemento_iva = ensureNotNull(conductorEntity.getComplementoIva());
        this.t000 = ensureNotNull(conductorEntity.getT000());
        this.t065 = ensureNotNull(conductorEntity.getT065());
        this.t060 = ensureNotNull(conductorEntity.getT060());
        this.t055 = ensureNotNull(conductorEntity.getT055());
        this.t050 = ensureNotNull(conductorEntity.getT050());
        this.creado = conductorEntity.getCreado();
        this.actualizado = conductorEntity.getActualizado();
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
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='node-root'><div><b>")
                .append(getClass().getSimpleName()).append("</b></div>")
                .append("<ul>");

        sb.append(Arrays.stream(getClass().getDeclaredFields()).map(e -> {
            try {
                Object o = e.get(this);
                return o != null? "<li>" + "<b>" + e.getName() + ": &nbsp;<b>" + o.toString() + "</li>": "";
            } catch (IllegalAccessException illegalAccessException) {
                return "<li>" + illegalAccessException.toString() + "</li>";
            }
        }).collect(Collectors.joining("\n")));


        return sb.append("</ul>").append("</div>").toString();
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

    public BigDecimal getT000() {
        return t000;
    }

    public void setT000(BigDecimal t000) {
        this.t000 = t000;
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
