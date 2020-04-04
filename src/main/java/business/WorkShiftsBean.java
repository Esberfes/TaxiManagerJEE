package business;

import database.WorkShiftsDbBean;
import datamodels.LazyLoad;
import enums.ShiftType;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Employee;
import pojos.License;
import pojos.WorkShift;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = WorkShiftsBean.BEAN_NAME)
public class WorkShiftsBean implements LazyLoad<WorkShift> {

    public static final String BEAN_NAME = "WorkShiftsBean";

    @Inject
    private WorkShiftsDbBean workShiftsDbBean;

    @Inject
    private EmployeesBean employeesBean;

    @Inject
    private LicensesBean licensesBean;

    @Override
    public List<WorkShift> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return workShiftsDbBean.getWorkShiftData(first, pageSize, sortMeta, filterMeta).stream().map(WorkShift::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return workShiftsDbBean.getWorkShiftTotal(filterMeta);
    }

    public void deleteWorkShift(Long id) {
        workShiftsDbBean.deleteWorkShift(id);
    }

    public void insertWorkShift(String licenseCode, Date day, String employeeName, ShiftType shiftType, Double income) throws Exception {
        try {
            List<Employee> employees = employeesBean.findEmployeesByFullName(employeeName);
            List<License> licenses = licensesBean.findLicensesByCod(licenseCode);
            workShiftsDbBean.insertWorkShift(new WorkShift(null, shiftType, day, employees.get(0), licenses.get(0), income));
        } catch (Throwable e) {
            throw new Exception(e.getMessage());
        }
    }
}
