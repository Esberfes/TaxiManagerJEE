package com.taxi.faces;

import com.taxi.business.FormasPagosBean;
import com.taxi.datamodels.LazyFormasPagoDataModel;
import com.taxi.singletons.TaxiLogger;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import com.taxi.pojos.FormasPago;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(FormasPagosFace.BEAN_NAME)
public class FormasPagosFace implements Serializable {

    public static final String BEAN_NAME = "FormasPagosFace";

    @Inject
    private FormasPagosBean formasPagosBean;

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<FormasPago> lazyModel;

    private String nombre;

    @PostConstruct
    public void init() {
        lazyModel = new LazyFormasPagoDataModel(formasPagosBean);
    }

    public void onRowEdit(RowEditEvent<FormasPago> event) {
        try {
            formasPagosBean.update(event.getObject());

             FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()) );
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<FormasPago> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada",  String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete(Long id) {
        try {
            formasPagosBean.delete(id);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminando forma de pago", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insert() {
        try {
            formasPagosBean.insert(new FormasPago(nombre));

            nombre = null;

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo forma de pago insertada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando forma de pago", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LazyDataModel<FormasPago> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<FormasPago> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
