package faces;

import business.LicensesBean;
import datamodels.LazyLicenseDataModel;
import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Employee;
import pojos.License;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(LicensesFace.BEAN_NAME)
public class LicensesFace implements Serializable {

    public static final String BEAN_NAME = "LicensesFace";

    @Inject
    private LicensesBean licensesBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<License> lazyModel;

    private String code;

    @PostConstruct
    public void init() {
        lazyModel = new LazyLicenseDataModel(licensesBean);
    }

    public LazyDataModel<License> getLazyModel() {
        return lazyModel;
    }

    public void onRowEdit(RowEditEvent<Employee> event) {
        FacesMessage msg = new FacesMessage("License Edited", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Employee> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insertLicense() {
        licensesBean.insertLicense(new License(code));
        code = null;
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo licencia insertada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteLicense(Long id) {
        licensesBean.deleteLicense(id);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
