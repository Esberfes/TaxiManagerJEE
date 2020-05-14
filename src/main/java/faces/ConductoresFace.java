package faces;

import business.ConductoresBean;
import datamodels.LazyConductorDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Conductor;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(ConductoresFace.BEAN_NAME)
public class ConductoresFace implements Serializable {

    public static final String BEAN_NAME = "ConductoresFace";

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<Conductor> lazyModel;

    private String nombre;


    @PostConstruct
    public void init() {
        lazyModel = new LazyConductorDataModel(conductoresBean);
    }


    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            Conductor conductor = (Conductor) ((DataTable) event.getComponent()).getRowData();
            conductoresBean.update(conductor);

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando empleado", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insert() {
        try {
            conductoresBean.insert(nombre);
            nombre = null;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo conductor insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando empleado", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void delete(Long id) {
        try {
            conductoresBean.delete(id);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conductor eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado empleado", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public LazyDataModel<Conductor> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Conductor> lazyModel) {
        this.lazyModel = lazyModel;
    }
}
