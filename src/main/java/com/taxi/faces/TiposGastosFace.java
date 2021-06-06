package com.taxi.faces;

import com.taxi.business.TiposGastosBean;
import com.taxi.datamodels.LazyTiposGastoDataModel;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import com.taxi.pojos.TiposGasto;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@ViewScoped
@Named(TiposGastosFace.BEAN_NAME)
public class TiposGastosFace implements Serializable {

    public static final String BEAN_NAME = "TiposGastosFace";

    @Inject
    private TiposGastosBean tiposGastosBean;

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<TiposGasto> lazyModel;

    private String nombre;
    private Boolean operacional;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyTiposGastoDataModel(tiposGastosBean);
    }

    public void onRowEdit(RowEditEvent<TiposGasto> event) {
        try {
            tiposGastosBean.update(event.getObject());
            logger.info("Tipo de gasto modificado: " + event.getObject().getId());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            logger.error("Error modificando tipo de gasto", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<TiposGasto> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void insert() {
        try {
            TiposGasto tiposGasto = new TiposGasto();
            tiposGasto.setNombre(nombre);
            tiposGasto.setEs_operacional(operacional);

            tiposGastosBean.insert(tiposGasto);

            nombre = null;
            operacional = null;

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva tipo de gasto insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            logger.error("Error insertando tipo de gasto", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando tipo de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void delete(Long id) {
        try {
            tiposGastosBean.delete(id);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de gasto eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            logger.error("Error eliminado tipo de gasto", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado tipo de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LazyDataModel<TiposGasto> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<TiposGasto> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getOperacional() {
        return operacional;
    }

    public void setOperacional(Boolean operacional) {
        this.operacional = operacional;
    }
}
