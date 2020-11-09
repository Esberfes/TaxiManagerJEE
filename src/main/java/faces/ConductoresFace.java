package faces;

import business.ConductoresBean;
import business.EmpresasBean;
import datamodels.LazyConductorDataModel;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Conductor;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;

@ViewScoped
@Named(ConductoresFace.BEAN_NAME)
public class ConductoresFace implements Serializable {

    public static final String BEAN_NAME = "ConductoresFace";

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private transient Logger logger;

    @Inject
    private EmpresasBean empresasBean;

    private LazyDataModel<Conductor> lazyModel;

    private String nombre;
    private String empresa;
    private BigDecimal complementoIva;
    private BigDecimal T065;
    private BigDecimal T060;
    private BigDecimal T055;
    private BigDecimal T050;

    @PostConstruct
    public void init() {
        lazyModel = new LazyConductorDataModel(conductoresBean);
    }


    public void onRowEdit(RowEditEvent<Conductor> event) {
        try {
            conductoresBean.update(event.getObject());

             FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()) );
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<Conductor> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada",  String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void insert() {
        try {
            Conductor conductor = new Conductor();
            conductor.setNombre(this.nombre);
            conductor.setEmpresa(empresasBean.findSingleByName(this.empresa));
            conductor.setComplemento_iva(this.complementoIva);
            conductor.setT065(this.T065);
            conductor.setT060(this.T060);
            conductor.setT055(this.T055);
            conductor.setT050(this.T050);

            conductoresBean.insert(conductor);

            this.nombre = null;
            this.empresa = null;
            this.complementoIva = null;
            this.T065 = null;
            this.T060 = null;
            this.T055 = null;
            this.T050 = null;

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo conductor insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando conductor", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void delete(Long id) {
        try {
            conductoresBean.delete(id);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conductor eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado conductor", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LazyDataModel<Conductor> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Conductor> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getComplementoIva() {
        return complementoIva;
    }

    public void setComplementoIva(BigDecimal complementoIva) {
        this.complementoIva = complementoIva;
    }

    public BigDecimal getT065() {
        return T065;
    }

    public void setT065(BigDecimal t065) {
        T065 = t065;
    }

    public BigDecimal getT060() {
        return T060;
    }

    public void setT060(BigDecimal t060) {
        T060 = t060;
    }

    public BigDecimal getT055() {
        return T055;
    }

    public void setT055(BigDecimal t055) {
        T055 = t055;
    }

    public BigDecimal getT050() {
        return T050;
    }

    public void setT050(BigDecimal t050) {
        T050 = t050;
    }
}
