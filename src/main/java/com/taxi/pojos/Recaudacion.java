package com.taxi.pojos;

import com.taxi.entities.RecaudacionIngresosEntity;
import com.taxi.entities.RecaudacionesEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.taxi.utils.BigDecimalUtils.ensureNotNull;

public class Recaudacion implements Identified{

    private Long id;
    private Licencia licencia;
    private List<RecaudacionIngreso> recaudacionIngresos;
    private Integer servicios_inicio;
    private Integer servicios_fin;
    private BigDecimal numeracion_inicio;
    private BigDecimal numeracion_fin;
    private Integer km_totales_inicio;
    private Integer km_totales_fin;
    private Integer km_cargado_inicio;
    private Integer km_cargado_fin;
    private Integer mes;
    private Integer ano;
    private Date creado;
    private Date actualizado;


    public Recaudacion() {
        this.recaudacionIngresos = new ArrayList<>();
    }

    public Recaudacion(RecaudacionesEntity recaudacionesEntity) {
        this.id = recaudacionesEntity.getId();
        this.licencia = new Licencia(recaudacionesEntity.getLicenciasEntity());

        this.recaudacionIngresos = new ArrayList<>();
        if (recaudacionesEntity.getRecaudacionIngresosEntities() != null) {
            for (RecaudacionIngresosEntity recaudacionIngresosEntity : recaudacionesEntity.getRecaudacionIngresosEntities()) {
                RecaudacionIngreso recaudacionIngreso = new RecaudacionIngreso(recaudacionIngresosEntity);
                recaudacionIngreso.setRecaudacionObj(null);
                this.recaudacionIngresos.add(recaudacionIngreso);
            }
        }

        this.servicios_inicio = recaudacionesEntity.getServiciosInicio();
        this.servicios_fin = recaudacionesEntity.getServiciosFin();
        this.numeracion_inicio = ensureNotNull(recaudacionesEntity.getNumeracionInicio());
        this.numeracion_fin = ensureNotNull(recaudacionesEntity.getNumeracionFin());
        this.km_totales_inicio = recaudacionesEntity.getKmTotalesInicio();
        this.km_totales_fin = recaudacionesEntity.getKmTotalesFin();
        this.km_cargado_inicio = recaudacionesEntity.getKmCargadoInicio();
        this.km_cargado_fin = recaudacionesEntity.getKmCargadoFin();
        this.mes = recaudacionesEntity.getMes();
        this.ano = recaudacionesEntity.getAno();
        this.creado = recaudacionesEntity.getCreado();
        this.actualizado = recaudacionesEntity.getActualizado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }

    public List<RecaudacionIngreso> getRecaudacionIngresos() {
        return recaudacionIngresos;
    }

    public void setRecaudacionIngresos(List<RecaudacionIngreso> recaudacionIngresos) {
        this.recaudacionIngresos = recaudacionIngresos;
    }

    public Integer getServicios_inicio() {
        return servicios_inicio;
    }

    public void setServicios_inicio(Integer servicios_inicio) {
        this.servicios_inicio = servicios_inicio;
    }

    public Integer getServicios_fin() {
        return servicios_fin;
    }

    public void setServicios_fin(Integer servicios_fin) {
        this.servicios_fin = servicios_fin;
    }

    public BigDecimal getNumeracion_inicio() {
        return numeracion_inicio;
    }

    public void setNumeracion_inicio(BigDecimal numeracion_inicio) {
        this.numeracion_inicio = numeracion_inicio;
    }

    public BigDecimal getNumeracion_fin() {
        return numeracion_fin;
    }

    public void setNumeracion_fin(BigDecimal numeracion_fin) {
        this.numeracion_fin = numeracion_fin;
    }

    public Integer getKm_totales_inicio() {
        return km_totales_inicio;
    }

    public void setKm_totales_inicio(Integer km_totales_inicio) {
        this.km_totales_inicio = km_totales_inicio;
    }

    public Integer getKm_totales_fin() {
        return km_totales_fin;
    }

    public void setKm_totales_fin(Integer km_totales_fin) {
        this.km_totales_fin = km_totales_fin;
    }

    public Integer getKm_cargado_inicio() {
        return km_cargado_inicio;
    }

    public void setKm_cargado_inicio(Integer km_cargado_inicio) {
        this.km_cargado_inicio = km_cargado_inicio;
    }

    public Integer getKm_cargado_fin() {
        return km_cargado_fin;
    }

    public void setKm_cargado_fin(Integer km_cargado_fin) {
        this.km_cargado_fin = km_cargado_fin;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
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

    public void addRecaudacionIngresos(RecaudacionIngreso recaudacionIngreso) {
        if (this.recaudacionIngresos == null)
            this.recaudacionIngresos = new ArrayList<>();

        this.recaudacionIngresos.add(recaudacionIngreso);
    }
}
