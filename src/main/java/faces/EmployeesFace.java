package faces;

import business.EmployeesBean;
import datamodels.LazyEmployeeDataModel;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Employee;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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

    public void onCellEdit(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            Employee employee = (Employee) ((DataTable) event.getComponent()).getRowData();
            employeesBean.update(employee);

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Anterior: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error actualizando empleado", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void insertEmployee() {
        try {
            employeesBean.insertEmployee(name, firstLastName, secondLastName, dni);
            name = null;
            firstLastName = null;
            secondLastName = null;
            dni = null;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo empleado insertado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insertando empleado", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void deleteEmployee(Long id) {
        try {
            employeesBean.deleteEmployee(id);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empleado eliminado con éxito", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error eliminado empleado", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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
