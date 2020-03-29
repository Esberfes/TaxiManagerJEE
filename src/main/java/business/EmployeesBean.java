package business;

import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Employee;
import singletons.MockerSingleton;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless(name = EmployeesBean.BEAN_NAME)
public class EmployeesBean implements LazyLoad<Employee> {

    public static final String BEAN_NAME = "EmployeeBean";

    @Inject
    private MockerSingleton mockerSingleton;

    @Override
    public List<Employee> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return mockerSingleton.getEmployeeData(first, pageSize, sortMeta, filterMeta);
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return mockerSingleton.getEmployeeTotal(filterMeta);
    }

    public void insertEmployee(String name, String firstLastName, String secondLastName, String dni) {
        mockerSingleton.insertEmployee(name, firstLastName, secondLastName, dni);
    }

    public void deleteEmployee(Long id) {
        mockerSingleton.deleteEmployee(id);
    }
}
