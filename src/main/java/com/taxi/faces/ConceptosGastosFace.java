package com.taxi.faces;

import com.taxi.business.ConceptosGastosBean;
import com.taxi.business.TiposGastosBean;
import com.taxi.datamodels.LazyConceptosGastosDataModel;
import com.taxi.pojos.ConceptosGastos;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(ConceptosGastosFace.BEAN_NAME)
public class ConceptosGastosFace implements Serializable {

    public static final String BEAN_NAME = "ConceptosGastosFace";

    @Inject
    private ConceptosGastosBean conceptosGastosBean;

    @Inject
    private TiposGastosBean tiposGastosBean;

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<ConceptosGastos> lazyModel;

    private String nombre;
    private String tipoGasto;

    @PostConstruct
    public void init() {
        lazyModel = new LazyConceptosGastosDataModel(conceptosGastosBean);
    }

    public void onRowEdit(RowEditEvent<ConceptosGastos> event) {
        try {
            conceptosGastosBean.update(event.getObject());

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<ConceptosGastos> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void insert() {
        try {
            ConceptosGastos conceptosGastos = new ConceptosGastos();
            conceptosGastos.setNombre(nombre);
            conceptosGastos.setTipoGasto(tiposGastosBean.findSingleByName(tipoGasto));

            conceptosGastosBean.insert(conceptosGastos);

            nombre = null;
            tipoGasto = null;

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo concepto de gasto insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando concepto de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void delete(Long id) {
        try {
            conceptosGastosBean.delete(id);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Concepto de gasto eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado concepto de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LazyDataModel<ConceptosGastos> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ConceptosGastos> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }
}
