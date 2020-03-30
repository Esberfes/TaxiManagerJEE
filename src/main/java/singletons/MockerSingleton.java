package singletons;

import com.github.javafaker.Faker;
import database.EmployeesDbBean;
import datamodels.LazySorter;
import enums.ShiftType;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Employee;
import pojos.Income;
import pojos.License;
import pojos.WorkShift;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Startup
@Singleton(name = MockerSingleton.BEAN_NAME)
public class MockerSingleton {

    public static final String BEAN_NAME = "Mocker";

    private List<Employee> employees;
    private List<License> licenses;
    private List<WorkShift> workShifts;

    @Inject
    private EmployeesDbBean employeesDbBean;

    @PostConstruct
    public void init() {
        Faker faker = new Faker();

        employeesDbBean.truncate();
        employees = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            Employee employee = new Employee(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.name().lastName(),
                    faker.idNumber().valid());

            employeesDbBean.insertEmployee(employee);
            employees.add(employee);
        }



        licenses = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            licenses.add(new License(faker.number().randomNumber(), faker.idNumber().valid()));

        workShifts = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            WorkShift workShift = new WorkShift();
            workShift.setId(faker.number().randomNumber());
            workShift.setShiftType(Math.random() > 0.5 ? ShiftType.afternoon : ShiftType.morning);
            Date start = faker.date().birthday();
            workShift.setStart(start);
            workShift.setEnd(faker.date().future(8, TimeUnit.HOURS, start));
            Random rand = new Random();
            workShift.setEmployee(employees.get(rand.nextInt(employees.size())));
            workShift.setLicense(licenses.get(rand.nextInt(licenses.size())));

            for (int x = 0; x < rand.nextInt(20); x++)
                workShift.addIncome(new Income(faker.number().randomNumber(), faker.number().randomDouble(2, 5, 180), workShift));

            workShifts.add(workShift);
        }

    }

    public void deleteEmployee(Long id) {
        employees.removeIf(e -> e.getId().equals(id));
    }

    public void insertEmployee(String name, String firstLastName, String secondLastName, String dni) {
        Faker faker = new Faker();
        employees.add(new Employee(name, firstLastName, secondLastName, dni));
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

    public List<WorkShift> getWorkShiftData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        List<WorkShift> data = filterData(filterMeta, workShifts);

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

    public int getWorkShiftTotal(Map<String, FilterMeta> filterMeta) {
        return filterData(filterMeta, workShifts).size();
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
