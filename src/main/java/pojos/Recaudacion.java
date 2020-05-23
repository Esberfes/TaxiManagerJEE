package pojos;

import entities.RecaudacionIngresosEntity;
import entities.RecaudacionesEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recaudacion {

    private Integer id;
    private Licencia licencia;
    private List<RecaudacionIngreso> recaudacionIngresos;
    private Integer serviciosInicio;
    private Integer servicionsFin;
    private BigDecimal numeracionInicio;
    private BigDecimal numeracionFin;
    private Integer kmTotalesInicio;
    private Integer kmTotalesFin;
    private Integer kmCargadoInicio;
    private Integer kmCargadoFin;
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
                this.recaudacionIngresos.add(recaudacionIngreso);
            }
        }

        this.serviciosInicio = recaudacionesEntity.getServiciosInicio();
        this.servicionsFin = recaudacionesEntity.getServicionsFin();
        this.numeracionInicio = recaudacionesEntity.getNumeracionInicio();
        this.numeracionFin = recaudacionesEntity.getNumeracionFin();
        this.kmTotalesInicio = recaudacionesEntity.getKmTotalesInicio();
        this.kmTotalesFin = recaudacionesEntity.getKmTotalesFin();
        this.kmCargadoInicio = recaudacionesEntity.getKmCargadoInicio();
        this.kmCargadoFin = recaudacionesEntity.getKmCargadoFin();
        this.mes = recaudacionesEntity.getMes();
        this.ano = recaudacionesEntity.getAno();
        this.creado = recaudacionesEntity.getCreado();
        this.actualizado = recaudacionesEntity.getActualizado();
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

    public List<RecaudacionIngreso> getRecaudacionIngresos() {
        return recaudacionIngresos;
    }

    public void setRecaudacionIngresos(List<RecaudacionIngreso> recaudacionIngresos) {
        this.recaudacionIngresos = recaudacionIngresos;
    }

    public Integer getServiciosInicio() {
        return serviciosInicio;
    }

    public void setServiciosInicio(Integer serviciosInicio) {
        this.serviciosInicio = serviciosInicio;
    }

    public Integer getServicionsFin() {
        return servicionsFin;
    }

    public void setServicionsFin(Integer servicionsFin) {
        this.servicionsFin = servicionsFin;
    }

    public BigDecimal getNumeracionInicio() {
        return numeracionInicio;
    }

    public void setNumeracionInicio(BigDecimal numeracionInicio) {
        this.numeracionInicio = numeracionInicio;
    }

    public BigDecimal getNumeracionFin() {
        return numeracionFin;
    }

    public void setNumeracionFin(BigDecimal numeracionFin) {
        this.numeracionFin = numeracionFin;
    }

    public Integer getKmTotalesInicio() {
        return kmTotalesInicio;
    }

    public void setKmTotalesInicio(Integer kmTotalesInicio) {
        this.kmTotalesInicio = kmTotalesInicio;
    }

    public Integer getKmTotalesFin() {
        return kmTotalesFin;
    }

    public void setKmTotalesFin(Integer kmTotalesFin) {
        this.kmTotalesFin = kmTotalesFin;
    }

    public Integer getKmCargadoInicio() {
        return kmCargadoInicio;
    }

    public void setKmCargadoInicio(Integer kmCargadoInicio) {
        this.kmCargadoInicio = kmCargadoInicio;
    }

    public Integer getKmCargadoFin() {
        return kmCargadoFin;
    }

    public void setKmCargadoFin(Integer kmCargadoFin) {
        this.kmCargadoFin = kmCargadoFin;
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
