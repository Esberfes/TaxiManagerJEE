package faces;

import business.EmployeesBean;
import business.LicensesBean;
import business.WorkShiftsBean;
import datamodels.LazyWorkShiftDataModel;
import enums.ShiftType;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import pojos.License;
import pojos.WorkShift;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named(WorkShiftsFace.BEAN_NAME)
public class WorkShiftsFace implements Serializable {

    public static final String BEAN_NAME = "WorkShiftsFace";

    @Inject
    private WorkShiftsBean workShiftsBean;

    @Inject
    private EmployeesBean employeesBean;

    @Inject
    private LicensesBean licensesBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<WorkShift> lazyModel;
    private WorkShift selectedWorkShift;

    private String employeeName;
    private String licenseCode;
    private Date day;
    private ShiftType shiftType;
    private Double income = (double) 0;

    @PostConstruct
    public void init() {
        lazyModel = new LazyWorkShiftDataModel(workShiftsBean);
    }

    public LazyDataModel<WorkShift> getLazyModel() {
        return lazyModel;
    }

    public void onRowEdit(RowEditEvent<WorkShift> event) {
        FacesMessage msg = new FacesMessage("License Edited", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<WorkShift> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        WorkShift workShift = (WorkShift) ((DataTable) event.getComponent()).getRowData();
        workShiftsBean.update(workShift);

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public WorkShift getSelectedWorkShift() {
        return selectedWorkShift;
    }

    public void onRowSelected(SelectEvent<WorkShift> event) {
        FacesMessage msg = new FacesMessage("Turno seleccionado: ", String.valueOf(selectedWorkShift.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setSelectedWorkShift(WorkShift selectedWorkShift) {
        this.selectedWorkShift = selectedWorkShift;
    }

    public void deleteWorkShift(Long id) {
        workShiftsBean.deleteWorkShift(id);
    }

    public List<String> completeEmployee(String value) {
        return employeesBean.findEmployeesByFullName(value)
                .stream().map(e -> e.getName() + " " + e.getFirstLastName() + " " + e.getSecondLastName()).collect(Collectors.toList());
    }

    public List<String> completeLicense(String value) {
        return licensesBean.findLicensesByCod(value).stream().map(License::getCode).collect(Collectors.toList());
    }

    public void insertWokShift() {
        try {
            workShiftsBean.insertWorkShift(licenseCode, day, employeeName, shiftType, income);

            income = 0D;
            shiftType = ShiftType.morning;

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva recaudación insertada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando recaudación", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public static String formatDate(Date date) {
        if (date != null) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(date);
        }
        return "";
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
