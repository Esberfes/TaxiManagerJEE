package faces;

import business.EstadosIngresosBean;
import datamodels.LazyEstadosIngresoDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.EstadosIngreso;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(EstadosIngresosFace.BEAN_NAME)
public class EstadosIngresosFace implements Serializable {

    public static final String BEAN_NAME = "EstadosIngresosFace";

    @Inject
    private EstadosIngresosBean estadosIngresosBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<EstadosIngreso> lazyModel;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyEstadosIngresoDataModel(estadosIngresosBean);
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            EstadosIngreso estadosIngreso = (EstadosIngreso) ((DataTable) event.getComponent()).getRowData();
            estadosIngresosBean.update(estadosIngreso);

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando estado de ingreso", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insert() {
        try {

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
        }
    }

    public LazyDataModel<EstadosIngreso> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<EstadosIngreso> lazyModel) {
        this.lazyModel = lazyModel;
    }
}
