package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.Employee;

import java.util.List;
import java.util.Map;

public class LazyEmployeeDataModel extends LazyDataModel<Employee> {

    private List<Employee> datasource;
    private LazyLoad<Employee> lazyLoad;

    public LazyEmployeeDataModel(LazyLoad<Employee> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public Employee getRowData(String rowKey) {
        for (Employee employee : datasource) {
            if (String.valueOf(employee.getId()).equals(rowKey)) {
                return employee;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Employee employee) {
        return employee.getId();
    }

    @Override
    public List<Employee> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}