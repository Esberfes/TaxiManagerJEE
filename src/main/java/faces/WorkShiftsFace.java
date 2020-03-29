package faces;

import business.WorkShiftsBean;
import datamodels.LazyWorkShiftDataModel;
import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import pojos.Employee;
import pojos.WorkShift;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(WorkShiftsFace.BEAN_NAME)
public class WorkShiftsFace implements Serializable {

    public static final String BEAN_NAME = "WorkShiftsFace";

    @Inject
    private WorkShiftsBean workShiftsBean;

    @Inject
    private transient Logger logger;

    private LazyDataModel<WorkShift> lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = new LazyWorkShiftDataModel(workShiftsBean);
    }

    public LazyDataModel<WorkShift> getLazyModel() {
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

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
