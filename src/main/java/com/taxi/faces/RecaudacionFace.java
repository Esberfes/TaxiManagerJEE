package com.taxi.faces;

import com.taxi.business.*;
import com.taxi.datamodels.LazyRecaudacionDataModel;
import com.taxi.datamodels.LazyRecaudacionIngresoDataModel;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import com.taxi.pojos.Recaudacion;
import com.taxi.pojos.RecaudacionIngreso;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

@ViewScoped
@Named(RecaudacionFace.BEAN_NAME)
public class RecaudacionFace implements Serializable {

    public static final String BEAN_NAME = "RecaudacionFace";

    @Inject
    private RecaudacionBean recaudacionBean;

    @Inject
    private RecaudacionIngresosBean recaudacionIngresosBean;

    @Inject
    private LicenciasBean licenciasBean;

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private EstadosIngresosBean estadosIngresosBean;

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<Recaudacion> lazyModel;
    private LazyDataModel<RecaudacionIngreso> lazyModelIngresos;

    private Recaudacion selectedRecaudacion;
    private RecaudacionIngreso selectedRecaudacionIngreso;

    // Insert recaudacion
    private Integer licencia;
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

    // Insert recaudacion ingreo
    private String conductor;
    private String estado;
    private Integer dia;
    private String turno;
    private BigDecimal numeracion;
    private BigDecimal anulados;
    private BigDecimal tarjeta;
    private BigDecimal app;
    private BigDecimal efectivo;
    private String observaciones;

    @PreDestroy
    public void destroy() {
        System.out.println();
    }

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyRecaudacionDataModel(recaudacionBean);
        Calendar calendar = new GregorianCalendar();
        ano = calendar.get(Calendar.YEAR) % 100;;
        mes = calendar.get(Calendar.MONTH)  + 1;
    }

    public void onRowIngresoEdit(RowEditEvent<RecaudacionIngreso> event) {
        try {
            recaudacionIngresosBean.update(event.getObject());

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().executeScript("PrimeFaces.info('Info message');");
            logger.info("Recaudación ingreso editado", event.getObject());
        } catch (Throwable e) {
            logger.error("Error modificando racaudación", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error editando reacaudación ingreso", e);
        }
    }

    public void onRowRecaudacionEdit(RowEditEvent<Recaudacion> event) {
        try {
            recaudacionBean.update(event.getObject());

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

            logger.info("Recaudación editada", event.getObject());
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error editando reacaudación", e);
        }
    }

    public void onRowCancel(RowEditEvent<?> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void insertIngreso() {
        try {
            RecaudacionIngreso ingreso = new RecaudacionIngreso();

            ingreso.setConductor(conductoresBean.findEmployeesByFullName(conductor).get(0));
            ingreso.setEstado(estadosIngresosBean.findSingleByName(estado));
            ingreso.setDia(dia);
            ingreso.setTurno(turno);
            ingreso.setNumeracion(numeracion);
            ingreso.setAnulados(anulados);
            ingreso.setTarjeta(tarjeta);
            ingreso.setApp(app);
            ingreso.setEfectivo(efectivo);
            ingreso.setObservaciones(observaciones);

            selectedRecaudacion.addRecaudacionIngresos(ingreso);

            recaudacionIngresosBean.insert(ingreso, selectedRecaudacion);

            this.estado = null;
            this.dia = null;
            this.turno = null;
            this.numeracion = null;
            this.anulados = null;
            this.tarjeta = null;
            this.app = null;
            this.efectivo = null;
            this.observaciones = null;

            logger.info("Recaudación ingreso insertado", ingreso);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando ingreso", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error insertando reacaudación ingreso", e);
        }
    }

    public void insert() {
        try {
            Recaudacion recaudacion = new Recaudacion();

            recaudacion.setLicencia(licenciasBean.findSingleByCodigo(licencia));

            recaudacion.setServicios_inicio(servicios_inicio);
            recaudacion.setServicios_fin(servicios_fin);

            recaudacion.setNumeracion_inicio(numeracion_inicio);
            recaudacion.setNumeracion_fin(numeracion_fin);

            recaudacion.setKm_totales_inicio(km_totales_inicio);
            recaudacion.setKm_totales_fin(km_totales_fin);

            recaudacion.setKm_cargado_inicio(km_cargado_inicio);
            recaudacion.setKm_cargado_fin(km_cargado_fin);

            recaudacion.setMes(mes);
            recaudacion.setAno(ano);

            recaudacionBean.insert(recaudacion);

            Calendar calendar = new GregorianCalendar();

            licencia = null;
            servicios_inicio = null;
            servicios_fin = null;
            numeracion_inicio = null;
            numeracion_fin = null;
            km_totales_inicio = null;
            km_totales_fin = null;
            km_cargado_inicio = null;
            km_cargado_fin = null;
            ano = calendar.get(Calendar.YEAR) % 100;;
            mes = calendar.get(Calendar.MONTH)  + 1;

            logger.info("Recaudación insertada", recaudacion);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva recaudacion insertada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando recaudacion", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error insertando reacaudación ingreso", e);
        }
    }

    public void delete(Long id) {
        try {
            recaudacionBean.delete(id);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "recaudacion eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            logger.info("Recaudación eliminada con id: " + id);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado recaudacion", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error eliminando reacaudación", e);
        }
    }

    public void deleteIngreso(Long id) {
        try {
            recaudacionIngresosBean.delete(id);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Recaudacion ingreso eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            logger.info("Recaudación ingreso eliminado con id: " + id);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado recaudacion", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error eliminando reacaudación ingreso", e);
        }
    }

    public LazyDataModel<Recaudacion> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Recaudacion> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public LazyDataModel<RecaudacionIngreso> getLazyModelIngresos() {
        return lazyModelIngresos;
    }

    public void setLazyModelIngresos(LazyDataModel<RecaudacionIngreso> lazyModelIngresos) {
        this.lazyModelIngresos = lazyModelIngresos;
    }

    public Integer getLicencia() {
        return licencia;
    }

    public void setLicencia(Integer licencia) {
        this.licencia = licencia;
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

    public Recaudacion getSelectedRecaudacion() {
        return selectedRecaudacion;
    }

    public void setSelectedRecaudacion(Recaudacion selectedRecaudacion) {
        this.selectedRecaudacion = selectedRecaudacion;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(BigDecimal tarjeta) {
        this.tarjeta = tarjeta;
    }

    public BigDecimal getApp() {
        return app;
    }

    public void setApp(BigDecimal app) {
        this.app = app;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public void onSelectedRecaudacion(Long id) {
        try {
            this.selectedRecaudacion = recaudacionBean.findSingle(id);
            this.lazyModelIngresos = new LazyRecaudacionIngresoDataModel(recaudacionIngresosBean, selectedRecaudacion.getId());
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error selecionando recaudacion", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onUnSelectedRecaudacion() {
        this.selectedRecaudacion = null;
        this.lazyModelIngresos = null;
    }

    public String resolveState(RecaudacionIngreso recaudacionIngreso) {
        return recaudacionIngreso.getEstado() != null ? recaudacionIngreso.getEstado().getNombre() : null;
    }

    public RecaudacionIngreso getSelectedRecaudacionIngreso() {
        return selectedRecaudacionIngreso;
    }

    public void setSelectedRecaudacionIngreso(RecaudacionIngreso selectedRecaudacionIngreso) {
        this.selectedRecaudacionIngreso = selectedRecaudacionIngreso;
    }
}
