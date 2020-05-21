package faces;

import business.TiposGastosBean;
import datamodels.LazyTiposGastoDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.TiposGasto;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(TiposGastosFace.BEAN_NAME)
public class TiposGastosFace implements Serializable {

    public static final String BEAN_NAME = "TiposGastosFace";

    @Inject
    private TiposGastosBean tiposGastosBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<TiposGasto> lazyModel;

    private String nombre;
    private Boolean operacional;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyTiposGastoDataModel(tiposGastosBean);
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            TiposGasto tiposGasto = (TiposGasto) ((DataTable) event.getComponent()).getRowData();

            tiposGastosBean.update(tiposGasto);

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando tipo de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado empleado", e.getMessage());
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
