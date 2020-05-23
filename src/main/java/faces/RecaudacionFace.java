package faces;

import business.RecaudacionBean;
import datamodels.LazyRecaudacionDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Recaudacion;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(RecaudacionFace.BEAN_NAME)
public class RecaudacionFace implements Serializable {

    public static final String BEAN_NAME = "RecaudacionFace";

    @Inject
    private RecaudacionBean recaudacionBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<Recaudacion> lazyModel;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyRecaudacionDataModel(recaudacionBean);
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            Recaudacion recaudacion = (Recaudacion) ((DataTable) event.getComponent()).getRowData();


            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando recaudacion", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insert() {
        try {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva recaudacion insertada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando recaudacion", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void delete(Long id) {
        try {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "recaudacion eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado recaudacion", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LazyDataModel<Recaudacion> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Recaudacion> lazyModel) {
        this.lazyModel = lazyModel;
    }
}
