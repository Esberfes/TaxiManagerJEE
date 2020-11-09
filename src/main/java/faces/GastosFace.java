package faces;

import business.ConceptosGastosBean;
import business.FormasPagosBean;
import business.GastosBean;
import business.LicenciasBean;
import datamodels.LazyGastosDataModel;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Gasto;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;

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
    private ConceptosGastosBean conceptosGastosBean;

    private LazyDataModel<Gasto> lazyModel;

    private Integer licencia;
    private String formaPago;
    private String concepto;
    private BigDecimal importe;

    @PostConstruct
    public void init() {
        this.lazyModel = new LazyGastosDataModel(gastosBean);
    }

    public void insert() {
        try {
            Gasto gasto = new Gasto();
            gasto.setLicencia(licenciasBean.findSingleByCodigo(licencia));
            gasto.setFormaPago(formasPagosBean.findSingleByName(formaPago));
            gasto.setConcepto(conceptosGastosBean.findSingleByName(concepto));
            gasto.setImporte(importe);

            gastosBean.insert(gasto);

            licencia = null;
            formaPago = null;
            concepto = null;
            importe = null;

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

    public ConceptosGastosBean getConceptosGastosBean() {
        return conceptosGastosBean;
    }

    public void setConceptosGastosBean(ConceptosGastosBean conceptosGastosBean) {
        this.conceptosGastosBean = conceptosGastosBean;
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
}
