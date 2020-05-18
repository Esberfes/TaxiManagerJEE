package faces;

import business.EmpresasBean;
import business.LicenciasBean;
import datamodels.LazyLicenciaDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Empresa;
import pojos.Licencia;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named(LicenciasFace.BEAN_NAME)
public class LicenciasFace implements Serializable {

    public static final String BEAN_NAME = "LicenciasFace";


    @Inject
    private LicenciasBean licenciasBean;

    @Inject
    private EmpresasBean empresasBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<Licencia> lazyModel;

    private Integer codigo;
    private String empresa;
    private Boolean esEuroTaxi;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyLicenciaDataModel(licenciasBean);
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            Licencia licencia = (Licencia) ((DataTable) event.getComponent()).getRowData();
            licenciasBean.update(licencia);

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando licencia", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insert() {
        try {
            Licencia licencia = new Licencia();
            licencia.setCodigo(codigo);
            licencia.setEmpresa(empresasBean.findSingleByName(this.empresa));
            licencia.setEs_eurotaxi(esEuroTaxi);

            licenciasBean.insert(licencia);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva licencia insertada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando licencia", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void delete(Long id) {
        try {
            licenciasBean.delete(id);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Licencia eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado licencia", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LazyDataModel<Licencia> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Licencia> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public List<String> completeEmpesa(String value) {
        try {
            return empresasBean.findByName(value).stream().map(Empresa::getNombre).collect(Collectors.toList());
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando empresas", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return new ArrayList<>();
        }
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Boolean getEsEuroTaxi() {
        return esEuroTaxi;
    }

    public void setEsEuroTaxi(Boolean esEuroTaxi) {
        this.esEuroTaxi = esEuroTaxi;
    }
}
