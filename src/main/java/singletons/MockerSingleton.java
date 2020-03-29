package singletons;

import com.github.javafaker.Faker;
import datamodels.LazySorter;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Employee;
import pojos.License;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Startup
@Singleton(name = MockerSingleton.BEAN_NAME)
public class MockerSingleton {

    public static final String BEAN_NAME = "Mocker";

    private List<Employee> employees;

    private List<License> licenses;

    @PostConstruct
    public void init() {
        Faker faker = new Faker();

        employees = new ArrayList<>();
        for (int i = 0; i < 300; i++)
            employees.add(new Employee(
                    faker.number().randomNumber(),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.name().lastName(),
                    faker.idNumber().valid())
            );

        licenses = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            licenses.add(new License(faker.number().randomNumber(), faker.idNumber().valid()));
    }

    public void deleteEmployee(Long id) {
        employees.removeIf(e -> e.getId().equals(id));
    }

    public void insertEmployee(String name, String firstLastName, String secondLastName, String dni) {
        Faker faker = new Faker();
        employees.add(new Employee(faker.number().randomNumber(), name, firstLastName, secondLastName, dni));
    }

    public List<Employee> getEmployeeData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        List<Employee> data = filterData(filterMeta, employees);

        // sort
        sortData(sortMeta, data);

        //rowCount
        int dataSize = data.size();

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

    public int getEmployeeTotal(Map<String, FilterMeta> filterMeta) {
        return filterData(filterMeta, employees).size();
    }

    public List<License> getLicenseData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        List<License> data = filterData(filterMeta, licenses);

        // sort
        sortData(sortMeta, data);

        //rowCount
        int dataSize = data.size();

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

    public int getLicenseTotal(Map<String, FilterMeta> filterMeta) {
        return filterData(filterMeta, licenses).size();
    }

    public void insertLicense(String code) {
        Faker faker = new Faker();
        licenses.add(new License(faker.number().randomNumber(), code));
    }

    public void deleteLicense(Long id) {
        licenses.removeIf(e -> e.getId().equals(id));
    }

    private <T> void sortData(Map<String, SortMeta> sortMeta, List<T> data) {
        if (sortMeta != null && !sortMeta.isEmpty()) {
            for (SortMeta meta : sortMeta.values()) {
                data.sort(new LazySorter<>(meta.getSortField(), meta.getSortOrder()));
            }
        }
    }

    private <T> List<T> filterData(Map<String, FilterMeta> filterMeta, List<T> source) {
        List<T> data = new ArrayList<>();
        //filter
        for (T employee : source) {
            boolean match = true;

            if (filterMeta != null) {
                for (FilterMeta meta : filterMeta.values()) {
                    try {
                        String filterField = meta.getFilterField();
                        Object filterValue = meta.getFilterValue();
                        Field field = employee.getClass().getDeclaredField(filterField);
                        field.setAccessible(true);
                        String fieldValue = String.valueOf(field.get(employee));

                        if (filterValue == null || StringUtils.isBlank(filterValue.toString()) || fieldValue.contains(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(employee);
            }
        }

        return data;
    }
}
