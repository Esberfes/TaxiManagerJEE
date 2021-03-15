package com.taxi.faces;

import com.taxi.business.EmpresasBean;
import com.taxi.business.LicenciasBean;
import com.taxi.datamodels.LazyLicenciaDataModel;
import com.taxi.pojos.Licencia;
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
@Named(LicenciasFace.BEAN_NAME)
public class LicenciasFace implements Serializable {

    public static final String BEAN_NAME = "LicenciasFace";


    @Inject
    private LicenciasBean licenciasBean;

    @Inject
    private EmpresasBean empresasBean;

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<Licencia> lazyModel;

    private Integer codigo;
    private String empresa;
    private Boolean esEuroTaxi;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyLicenciaDataModel(licenciasBean);
    }

    public void onRowEdit(RowEditEvent<Licencia> event) {
        try {
            licenciasBean.update(event.getObject());

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

            logger.info("Licencia editada", event.getObject());
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error editando licencia", e);
        }
    }

    public void onRowCancel(RowEditEvent<Licencia> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void insert() {
        try {
            Licencia licencia = new Licencia();
            licencia.setCodigo(codigo);
            licencia.setEmpresa(empresasBean.findSingleByName(this.empresa));
            licencia.setEs_eurotaxi(esEuroTaxi);

            licenciasBean.insert(licencia);

            codigo = null;
            empresa = null;
            esEuroTaxi = null;

            logger.info("Licencia insertada", licencia);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva licencia insertada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando licencia", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error insertando licencia" , e);
        }
    }

    public void delete(Long id) {
        try {
            licenciasBean.delete(id);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Licencia eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            logger.info("Licencia eliminada con id.: ", id);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado licencia", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error eliminando licencia con id: " + id, e);
        }
    }

    public LazyDataModel<Licencia> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Licencia> lazyModel) {
        this.lazyModel = lazyModel;
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
