package com.taxi.pojos;

import com.taxi.entities.RecaudacionIngresosEntity;

import java.math.BigDecimal;
import java.util.Date;

import static com.taxi.utils.BigDecimalUtils.ensureNotNull;

public class RecaudacionIngreso {

    private Long id;
    private Recaudacion recaudacionObj;
    private Conductor conductor;
    private EstadosIngreso estado;
    private Integer dia;
    private String turno;
    private BigDecimal numeracion;
    private BigDecimal anulados;
    private BigDecimal recaudacion;
    private BigDecimal liquido;
    private String observaciones;
    private Date creado;
    private Date actualizado;

    public RecaudacionIngreso() {
    }

    public RecaudacionIngreso(RecaudacionIngresosEntity recaudacionIngresosEntity) {
        this.id = recaudacionIngresosEntity.getId();
        this.recaudacionObj = null;
        this.conductor = new Conductor(recaudacionIngresosEntity.getConductorEntity());
        this.estado = recaudacionIngresosEntity.getRecaudacionesIngresosEstadosEntity() != null ?
                new EstadosIngreso(recaudacionIngresosEntity.getRecaudacionesIngresosEstadosEntity()) : new EstadosIngreso();
        this.dia = recaudacionIngresosEntity.getDia();
        this.turno = recaudacionIngresosEntity.getTurno();
        this.numeracion = ensureNotNull(recaudacionIngresosEntity.getNumeracion());
        this.liquido = recaudacionIngresosEntity.getLiquido();
        this.anulados = ensureNotNull(recaudacionIngresosEntity.getAnulados());
        this.recaudacion = ensureNotNull(recaudacionIngresosEntity.getRecaudacion());
        this.observaciones = recaudacionIngresosEntity.getObservaciones();
        this.creado = recaudacionIngresosEntity.getCreado();
        this.actualizado = recaudacionIngresosEntity.getActualizado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public EstadosIngreso getEstado() {
        return estado;
    }

    public void setEstado(EstadosIngreso estado) {
        this.estado = estado;
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

    public Recaudacion getRecaudacionObj() {
        return recaudacionObj;
    }

    public void setRecaudacionObj(Recaudacion recaudacionObj) {
        this.recaudacionObj = recaudacionObj;
    }

    public BigDecimal getLiquido() {
        return liquido;
    }

    public void setLiquido(BigDecimal liquido) {
        this.liquido = liquido;
    }
}

