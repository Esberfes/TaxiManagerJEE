package com.taxi.faces;

import com.taxi.business.*;
import com.taxi.datamodels.LazyGastosDataModel;
import com.taxi.pojos.Gasto;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ViewScoped
@Named(GastosFace.BEAN_NAME)
public class GastosFace implements Serializable {

    public static final String BEAN_NAME = "GastosFace";

    @Inject
    private GastosBean gastosBean;

    @Inject
    private LicenciasBean licenciasBean;

    @Inject
    private FormasPagosBean formasPagosBean;

    @Inject
    private TiposGastosBean tiposGastosBean;

    private LazyDataModel<Gasto> lazyModel;

    private Integer licencia;
    private String formaPago;
    private String concepto;
    private BigDecimal importe;
    private String definicion;
    private Date fechaFactura;
    private String tipoGasto;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyGastosDataModel(gastosBean);
    }

    public void insert() {
        try {
            Gasto gasto = new Gasto();
            gasto.setLicencia(licenciasBean.findSingleByCodigo(licencia));
            gasto.setFormaPago(formasPagosBean.findSingleByName(formaPago));
            if(tipoGasto != null)
                gasto.setTipoGasto(tiposGastosBean.findSingleByName(tipoGasto));

            gasto.setImporte(importe);
            gasto.setDefinicion(definicion);
            gasto.setFechaFactura(fechaFactura);
            gastosBean.insert(gasto);

            licencia = null;
            formaPago = null;
            concepto = null;
            importe = null;
            definicion = null;
            fechaFactura = null;
            tipoGasto = null;

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo concepto de gasto insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando concepto de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowEdit(RowEditEvent<Gasto> event) {
        try {
            gastosBean.update(event.getObject());

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrada modificada", String.valueOf(event.getObject().getId()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando entrada", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent<Gasto> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete(Long id) {
        try {
            gastosBean.delete(id);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Gasto eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public GastosBean getGastosBean() {
        return gastosBean;
    }

    public void setGastosBean(GastosBean gastosBean) {
        this.gastosBean = gastosBean;
    }

    public LazyDataModel<Gasto> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Gasto> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public LicenciasBean getLicenciasBean() {
        return licenciasBean;
    }

    public void setLicenciasBean(LicenciasBean licenciasBean) {
        this.licenciasBean = licenciasBean;
    }

    public FormasPagosBean getFormasPagosBean() {
        return formasPagosBean;
    }

    public void setFormasPagosBean(FormasPagosBean formasPagosBean) {
        this.formasPagosBean = formasPagosBean;
    }

    public Integer getLicencia() {
        return licencia;
    }

    public void setLicencia(Integer licencia) {
        this.licencia = licencia;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFacturacion) {
        this.fechaFactura = fechaFacturacion;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }
}
