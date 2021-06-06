package com.taxi.faces;

import com.taxi.business.EstadosIngresosBean;
import com.taxi.datamodels.LazyEstadosIngresoDataModel;
import com.taxi.entities.RecaudacionesIngresosEstadosEntity;
import com.taxi.pojos.EstadosIngreso;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@ViewScoped
@Named(EstadosIngresosFace.BEAN_NAME)
public class EstadosIngresosFace implements Serializable {

    public static final String BEAN_NAME = "EstadosIngresosFace";

    @Inject
    private EstadosIngresosBean estadosIngresosBean;

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<EstadosIngreso> lazyModel;

    private String nombre;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyEstadosIngresoDataModel(estadosIngresosBean);
    }

    public void onRowEdit(RowEditEvent<EstadosIngreso> event) {
        try {
            estadosIngresosBean.update(event.getObject());

            logger.info("Estado ingreso modificado: " + event.getObject().getId());

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<EstadosIngreso> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void insert() {
        try {
            EstadosIngreso estadosIngreso = new EstadosIngreso(nombre);

            RecaudacionesIngresosEstadosEntity estadosEntity =  estadosIngresosBean.insert(estadosIngreso);

            logger.info("Estado ingreso insertado: " + estadosEntity.getId());

            nombre = null;

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva estado de ingreso insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando estado de ingreso", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void delete(Long id) {
        try {
            estadosIngresosBean.delete(id);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado de ingreso eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado estado de ingreso", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error eliminado estado de ingreso", e);
        }
    }

    public LazyDataModel<EstadosIngreso> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<EstadosIngreso> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
