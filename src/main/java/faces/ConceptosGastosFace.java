package faces;

import business.ConceptosGastosBean;
import datamodels.LazyConceptosGastosDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.ConceptosGastos;

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
    private transient Logger logger;

    private LazyDataModel<ConceptosGastos> lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = new LazyConceptosGastosDataModel(conceptosGastosBean);
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            ConceptosGastos conceptosGastos = (ConceptosGastos) ((DataTable) event.getComponent()).getRowData();
            conceptosGastosBean.update(conceptosGastos);

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando concepto de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insert() {
        try {

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
}
