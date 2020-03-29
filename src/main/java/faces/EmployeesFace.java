package faces;

import business.EmployeesBean;
import datamodels.LazyEmployeeDataModel;
import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Employee;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named(EmployeesFace.BEAN_NAME)
public class EmployeesFace implements Serializable {

    public static final String BEAN_NAME = "EmployeesFace";

    @Inject
    private EmployeesBean employeesBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<Employee> lazyModel;

    private String name;
    private String firstLastName;
    private String secondLastName;
    private String dni;

    @PostConstruct
    public void init() {
        lazyModel = new LazyEmployeeDataModel(employeesBean);
    }

    public LazyDataModel<Employee> getLazyModel() {
        return lazyModel;
    }

    public void onRowEdit(RowEditEvent<Employee> event) {
        FacesMessage msg = new FacesMessage("Car Edited", String.valueOf(event.getObject().getId()));
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

    public void insertEmployee() {
        employeesBean.insertEmployee(name, firstLastName, secondLastName, dni);
        name = null;
        firstLastName = null;
        secondLastName = null;
        dni = null;
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo empleado insertado", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteEmployee(Long id) {
        employeesBean.deleteEmployee(id);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empleado eliminado con éxito", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
