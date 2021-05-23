package com.taxi.entities;

import com.taxi.pojos.RecaudacionIngreso;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recaudacion_ingresos")
public class RecaudacionIngresosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_recaudacion", referencedColumnName = "id")
    private RecaudacionesEntity recaudacionesEntity;

    @ManyToOne
    @JoinColumn(name = "id_conductor", referencedColumnName = "id")
    private ConductorEntity conductorEntity;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    private RecaudacionesIngresosEstadosEntity recaudacionesIngresosEstadosEntity;

    @Column(name = "dia")
    private Integer dia;

    @Column(name = "t")
    private BigDecimal t;

    @Column(name = "turno")
    private String turno;

    @Column(name = "numeracion")
    private BigDecimal numeracion;

    @Column(name = "anulados")
    private BigDecimal anulados;

    @Column(name = "tarjeta")
    private BigDecimal tarjeta;

    @Column(name = "pagos")
    private BigDecimal pagos;

    @Column(name = "efectivo")
    private BigDecimal efectivo;

    @Column(name = "app")
    private BigDecimal app;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;

    @Transient
    private BigDecimal recaudacion;

    @Transient
    private BigDecimal liquido;

    public RecaudacionIngresosEntity() {
    }

    public RecaudacionIngresosEntity(RecaudacionIngreso recaudacionIngreso) {
        this.id = recaudacionIngreso.getId();
        this.conductorEntity = new ConductorEntity(recaudacionIngreso.getConductor());
        this.recaudacionesIngresosEstadosEntity = recaudacionIngreso.getEstado() != null ?
                new RecaudacionesIngresosEstadosEntity(recaudacionIngreso.getEstado()) : null;
        this.dia = recaudacionIngreso.getDia();
        this.turno = recaudacionIngreso.getTurno();
        this.numeracion = recaudacionIngreso.getNumeracion();
        this.anulados = recaudacionIngreso.getAnulados();
        this.recaudacion = recaudacionIngreso.getRecaudacion();
        this.liquido = recaudacionIngreso.getLiquido();
        this.tarjeta = recaudacionIngreso.getTarjeta();
        this.pagos = recaudacionIngreso.getPagos();
        this.efectivo = recaudacionIngreso.getEfectivo();
        this.app = recaudacionIngreso.getApp();
        this.observaciones = recaudacionIngreso.getObservaciones();
        this.creado = recaudacionIngreso.getCreado();
        this.actualizado = recaudacionIngreso.getActualizado();
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
        RecaudacionIngresosEntity that = (RecaudacionIngresosEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dia, that.dia) &&
                Objects.equals(turno, that.turno) &&
                Objects.equals(numeracion, that.numeracion) &&
                Objects.equals(anulados, that.anulados) &&
                Objects.equals(recaudacion, that.recaudacion) &&
                Objects.equals(observaciones, that.observaciones) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dia, turno, numeracion, anulados, recaudacion, observaciones, creado, actualizado);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecaudacionesEntity getRecaudacionesEntity() {
        return recaudacionesEntity;
    }

    public void setRecaudacionesEntity(RecaudacionesEntity recaudacionesEntity) {
        this.recaudacionesEntity = recaudacionesEntity;
    }

    public ConductorEntity getConductorEntity() {
        return conductorEntity;
    }

    public void setConductorEntity(ConductorEntity conductorEntity) {
        this.conductorEntity = conductorEntity;
    }

    public RecaudacionesIngresosEstadosEntity getRecaudacionesIngresosEstadosEntity() {
        return recaudacionesIngresosEstadosEntity;
    }

    public void setRecaudacionesIngresosEstadosEntity(RecaudacionesIngresosEstadosEntity recaudacionesIngresosEstadosEntity) {
        this.recaudacionesIngresosEstadosEntity = recaudacionesIngresosEstadosEntity;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public BigDecimal getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(BigDecimal numeracion) {
        this.numeracion = numeracion;
    }

    public BigDecimal getAnulados() {
        return anulados;
    }

    public void setAnulados(BigDecimal anulados) {
        this.anulados = anulados;
    }

    public BigDecimal getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(BigDecimal recaudacion) {
        this.recaudacion = recaudacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getPagos() {
        return pagos;
    }

    public void setPagos(BigDecimal pagos) {
        this.pagos = pagos;
    }

    public BigDecimal getApp() {
        return app;
    }

    public void setApp(BigDecimal app) {
        this.app = app;
    }

    public BigDecimal getT() {
        return t;
    }

    public void setT(BigDecimal t) {
        this.t = t;
    }
}
