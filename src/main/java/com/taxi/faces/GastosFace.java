package com.taxi.faces;

import com.taxi.business.FormasPagosBean;
import com.taxi.business.GastosBean;
import com.taxi.business.LicenciasBean;
import com.taxi.business.TiposGastosBean;
import com.taxi.datamodels.LazyGastosDataModel;
import com.taxi.pojos.Gasto;
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
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    @Inject
    private transient TaxiLogger logger;

    private LazyDataModel<Gasto> lazyModel;

    private Integer licencia;
    private String formaPago;
    private String concepto;
    private BigDecimal importe;
    private String definicion;
    private Integer mes;
    private Integer ano;
    private Date fechaFactura;
    private String tipoGasto;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyGastosDataModel(gastosBean);
        Calendar calendar = new GregorianCalendar();
        ano = calendar.get(Calendar.YEAR) % 100;;
        mes = calendar.get(Calendar.MONTH) ;
    }

    public void insert() {
        try {
            Gasto gasto = new Gasto();
            gasto.setLicencia(licenciasBean.findSingleByCodigo(licencia));
            gasto.setFormaPago(formasPagosBean.findSingleByName(formaPago));
            if (tipoGasto != null)
                gasto.setTipoGasto(tiposGastosBean.findSingleByName(tipoGasto));

            gasto.setImporte(importe);
            gasto.setDefinicion(definicion);
            gasto.setFechaFactura(fechaFactura);
            gasto.setMes(mes);
            gasto.setAno(ano);
            gastosBean.insert(gasto);

            Calendar calendar = new GregorianCalendar();
            licencia = null;
            formaPago = null;
            concepto = null;
            importe = null;
            definicion = null;
            ano = calendar.get(Calendar.YEAR) % 100;;
            mes = calendar.get(Calendar.MONTH)  + 1;
            fechaFactura = null;
            tipoGasto = null;
            logger.info("Gasto insertado", gasto);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo concepto de gasto insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando concepto de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("Error insertando gasto", e);
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
            logger.error("Error editando gasto", e);
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
            logger.error("Error eliminando gasto", e);
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

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
