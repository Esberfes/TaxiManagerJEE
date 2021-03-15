package com.taxi.entities;

import com.taxi.pojos.EmptyLicencia;
import com.taxi.pojos.Gasto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "gastos")
public class GastosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_licencia", referencedColumnName = "id")
    private LicenciasEntity licenciasEntity;

    @ManyToOne
    @JoinColumn(name = "id_forma_pago", referencedColumnName = "id")
    private FormasPagosGastosEntity formasPagosGastosEntity;

    @ManyToOne
    @JoinColumn(name = "id_tipo_gasto", referencedColumnName = "id")
    private TiposGastosEntity tiposGastosEntity;

    @Column(name = "importe", nullable = false)
    private BigDecimal importe;

    @Column(name = "definicion", nullable = false)
    private String definicion;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "fecha_factura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFactura;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;

    public GastosEntity() {

    }

    public GastosEntity(Gasto gasto) {
        this.id = gasto.getId();
        this.licenciasEntity = gasto.getLicencia() != null && !(gasto.getLicencia() instanceof EmptyLicencia) ? new LicenciasEntity(gasto.getLicencia()) : null;
        this.formasPagosGastosEntity = new FormasPagosGastosEntity(gasto.getFormaPago());
        this.tiposGastosEntity = gasto.getTipoGasto() != null?  new TiposGastosEntity(gasto.getTipoGasto()) : null;
        this.importe = gasto.getImporte();
        this.fechaFactura = gasto.getFechaFactura();
        this.definicion = gasto.getDefinicion();
        this.mes = gasto.getMes();
        this.ano = gasto.getAno();
        this.actualizado = gasto.getActualizado();
        this.creado = gasto.getCreado();
    }

    @PrePersist
    public void prePersist() {
        creado = new Date();
        actualizado = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        actualizado = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GastosEntity that = (GastosEntity) o;

        if (!id.equals(that.id)) return false;
        if (!licenciasEntity.equals(that.licenciasEntity)) return false;
        if (!formasPagosGastosEntity.equals(that.formasPagosGastosEntity)) return false;
        if (!tiposGastosEntity.equals(that.tiposGastosEntity)) return false;
        if (!importe.equals(that.importe)) return false;
        if (!definicion.equals(that.definicion)) return false;
        if (!mes.equals(that.mes)) return false;
        if (!ano.equals(that.ano)) return false;
        if (!fechaFactura.equals(that.fechaFactura)) return false;
        if (!creado.equals(that.creado)) return false;
        return actualizado.equals(that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, importe, creado, actualizado);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LicenciasEntity getLicenciasEntity() {
        return licenciasEntity;
    }

    public void setLicenciasEntity(LicenciasEntity licenciasEntity) {
        this.licenciasEntity = licenciasEntity;
    }

    public FormasPagosGastosEntity getFormasPagosGastosEntity() {
        return formasPagosGastosEntity;
    }

    public void setFormasPagosGastosEntity(FormasPagosGastosEntity formasPagosGastosEntity) {
        this.formasPagosGastosEntity = formasPagosGastosEntity;
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

    public TiposGastosEntity getTiposGastosEntity() {
        return tiposGastosEntity;
    }

    public void setTiposGastosEntity(TiposGastosEntity tiposGastosEntity) {
        this.tiposGastosEntity = tiposGastosEntity;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
