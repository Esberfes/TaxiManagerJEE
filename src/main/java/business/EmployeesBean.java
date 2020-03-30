package business;

import database.EmployeesDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Employee;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = EmployeesBean.BEAN_NAME)
public class EmployeesBean implements LazyLoad<Employee> {

    public static final String BEAN_NAME = "EmployeeBean";

    @Inject
    private EmployeesDbBean employeesDbBean;

    @Override
    public List<Employee> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return employeesDbBean.getEmployeeData(first, pageSize, sortMeta, filterMeta).stream().map(Employee::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return employeesDbBean.getTotalEmployees(filterMeta);
    }

    public void insertEmployee(String name, String firstLastName, String secondLastName, String dni) {
        employeesDbBean.insertEmployee(new Employee(name, firstLastName, secondLastName, dni));
    }

    public void deleteEmployee(Long id) {
        employeesDbBean.deleteEmployee(id);
    }
}
