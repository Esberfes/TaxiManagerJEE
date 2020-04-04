package singletons;

import database.EmployeesDbBean;
import database.LicensesDbBean;
import database.WorkShiftsDbBean;
import pojos.Employee;
import pojos.License;
import pojos.WorkShift;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;

@Startup
@Singleton(name = MockerSingleton.BEAN_NAME)
public class MockerSingleton {

    public static final String BEAN_NAME = "Mocker";

    private List<Employee> employees;
    private List<License> licenses;
    private List<WorkShift> workShifts;

    @Inject
    private EmployeesDbBean employeesDbBean;

    @Inject
    private LicensesDbBean licensesDbBean;

    @Inject
    private WorkShiftsDbBean workShiftsDbBean;

    @PostConstruct
    public void init() {
        /*
        Faker faker = new Faker();

        employeesDbBean.truncate();
        employees = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            Employee employee = new Employee(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.name().lastName(),
                    faker.idNumber().valid());

            EmployeeEntity e = employeesDbBean.insertEmployee(employee);
            employee.setId(e.getId());
            employees.add(employee);
        }

        licensesDbBean.truncate();
        licenses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            License license = new License(faker.idNumber().valid());
            LicenseEntity e = licensesDbBean.insertLicense(license);
            license.setId(e.getId());
            licenses.add(license);
        }

        workShiftsDbBean.truncate();
        workShifts = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            WorkShift workShift = new WorkShift();
            workShift.setId(faker.number().randomNumber());
            workShift.setShiftType(Math.random() > 0.5 ? ShiftType.afternoon : ShiftType.morning);
            Date day = faker.date().past(365, TimeUnit.DAYS);
            workShift.setDay(day);
            Random rand = new Random();
            workShift.setEmployee(employees.get(rand.nextInt(employees.size())));
            workShift.setLicense(licenses.get(rand.nextInt(licenses.size())));
            workShift.setIncome(faker.number().randomDouble(2, 5, 200));

            workShifts.add(workShift);
            workShiftsDbBean.insertWorkShift(workShift);
        }
        */
    }
}
