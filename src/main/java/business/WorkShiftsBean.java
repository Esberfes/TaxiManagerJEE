package business;

import database.WorkShiftsDbBean;
import datamodels.LazyLoad;
import enums.ShiftType;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Employee;
import pojos.IncomesSummary;
import pojos.License;
import pojos.WorkShift;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

    public void update(WorkShift workShift) {
        workShiftsDbBean.updateWorkShift(workShift);
    }

    public IncomesSummary generateSummary(Date start, Date end) {
        List<WorkShift> workShifts = workShiftsDbBean.getWorkShiftsBetween(start, end).stream().map(WorkShift::new).collect(Collectors.toList());
        MathContext mc = new MathContext(4);
        BigDecimal total = new BigDecimal(0, mc);
        Map<License, BigDecimal> licenseIncomes = new HashMap<>();
        Map<String, BigDecimal> dateIncomes = new HashMap<>();

        long diff = end.getTime() - start.getTime();
        String patter = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 31 ? "MM/yyyy" : "dd/MM/yyyy";

        for (WorkShift workShift : workShifts) {
            total = total.add(new BigDecimal(workShift.getIncome(), mc));
            DateFormat dateFormat = new SimpleDateFormat(patter);
            String date = dateFormat.format(workShift.getDay());

            if (!dateIncomes.containsKey(date))
                dateIncomes.put(date, new BigDecimal(workShift.getIncome(), mc));
            else
                dateIncomes.put(date, dateIncomes.get(date).add(new BigDecimal(workShift.getIncome(), mc)));

            if (!licenseIncomes.containsKey(workShift.getLicense()))
                licenseIncomes.put(workShift.getLicense(), new BigDecimal(workShift.getIncome(), mc));
            else
                licenseIncomes.put(workShift.getLicense(), licenseIncomes.get(workShift.getLicense()).add(new BigDecimal(workShift.getIncome(), mc)));
        }

        return new IncomesSummary(dateIncomes, licenseIncomes, total, start, end);
    }
}
