package pojos;

import entities.RecaudacionIngresosEntity;

import java.math.BigDecimal;
import java.util.Date;

public class RecaudacionIngreso {

    private Integer id;
    private Conductor conductor;
    private EstadosIngreso estadosIngreso;
    private Integer dia;
    private String turno;
    private BigDecimal numeracion;
    private BigDecimal anulados;
    private BigDecimal recaudacion;
    private String observaciones;
    private Date creado;
    private Date actualizado;

    public RecaudacionIngreso() {
    }

    public RecaudacionIngreso(RecaudacionIngresosEntity recaudacionIngresosEntity) {
        this.id = recaudacionIngresosEntity.getId();
        this.conductor = new Conductor(recaudacionIngresosEntity.getConductorEntity());
        this.estadosIngreso = new EstadosIngreso(recaudacionIngresosEntity.getRecaudacionesIngresosEstadosEntity());
        this.dia = recaudacionIngresosEntity.getDia();
        this.turno = recaudacionIngresosEntity.getTurno();
        this.numeracion = recaudacionIngresosEntity.getNumeracion();
        this.anulados = recaudacionIngresosEntity.getAnulados();
        this.recaudacion = recaudacionIngresosEntity.getRecaudacion();
        this.observaciones = recaudacionIngresosEntity.getObservaciones();
        this.creado = recaudacionIngresosEntity.getCreado();
        this.actualizado = recaudacionIngresosEntity.getActualizado();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public EstadosIngreso getEstadosIngreso() {
        return estadosIngreso;
    }

    public void setEstadosIngreso(EstadosIngreso estadosIngreso) {
        this.estadosIngreso = estadosIngreso;
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
}
