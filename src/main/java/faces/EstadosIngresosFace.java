package faces;

import business.EstadosIngresosBean;
import datamodels.LazyEstadosIngresoDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Conductor;
import pojos.EstadosIngreso;
import pojos.RecaudacionIngreso;

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

    private String nombre;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyEstadosIngresoDataModel(estadosIngresosBean);
    }

    public void onRowEdit(RowEditEvent<EstadosIngreso> event) {
        try {
            estadosIngresosBean.update(event.getObject());

             FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()) );
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<RecaudacionIngreso> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada",  String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void insert() {
        try {
            EstadosIngreso estadosIngreso = new EstadosIngreso(nombre);

            estadosIngresosBean.insert(estadosIngreso);

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
