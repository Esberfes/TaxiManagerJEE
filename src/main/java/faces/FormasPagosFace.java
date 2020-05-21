package faces;

import business.FormasPagosBean;
import datamodels.LazyFormasPagoDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.FormasPago;

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
    private transient Logger logger;

    private LazyDataModel<FormasPago> lazyModel;

    private String nombre;

    @PostConstruct
    public void init() {
        lazyModel = new LazyFormasPagoDataModel(formasPagosBean);
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            FormasPago formasPago = (FormasPago) ((DataTable) event.getComponent()).getRowData();

            formasPagosBean.update(formasPago);

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando forma de pago", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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
