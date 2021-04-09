package com.taxi.faces;

import com.taxi.business.EmpresasBean;
import com.taxi.datamodels.LazyEmpresaDataModel;
import com.taxi.entities.EmpresasEntity;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import com.taxi.pojos.Empresa;
import com.taxi.singletons.TaxiLogger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@ViewScoped
@Named(EmpresasFace.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class EmpresasFace implements Serializable {

    public static final String BEAN_NAME = "EmpresasFace";

    @Inject
    private EmpresasBean empresasBean;

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<Empresa> lazyModel;

    private String nombre;

    @PostConstruct
    public void init() {
        lazyModel = new LazyEmpresaDataModel(empresasBean);
    }

    public void onRowEdit(RowEditEvent<Empresa> event) {
        try {
            empresasBean.update(event.getObject());
            logger.info("Empresa modificada: " + event.getObject().getId());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empresa modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            logger.error("Error actualizando empresa", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<Empresa> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete(Long id) {
        try {
            empresasBean.delete(id);
            logger.info("Empresa eliminada: " + id);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminando empresa", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error eliminado empresa", e);
        }
    }

    public void insert() {
        try {
            EmpresasEntity insert = empresasBean.insert(new Empresa(nombre));
            nombre = null;

            logger.info("Empresa insetada: " + insert.getId());

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva empresa insertada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            logger.error("Error insertamdo empresa", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando empresa", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LazyDataModel<Empresa> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Empresa> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
