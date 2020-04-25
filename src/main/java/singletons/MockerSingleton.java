package singletons;

import business.ConductoresBean;
import business.EmpresasBean;
import com.github.javafaker.Faker;
import entities.ConductorEntity;
import entities.EmpresasEntity;
import pojos.Conductor;
import pojos.Empresa;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton(name = MockerSingleton.BEAN_NAME)
public class MockerSingleton {

    public static final String BEAN_NAME = "Mocker";

    private List<Conductor> conductores;
    private List<Empresa> empresas;

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private EmpresasBean empresasBean;

    @PostConstruct
    public void init() {
        Faker faker = new Faker();
        conductoresBean.truncate();
        empresasBean.truncate();

        conductores = new ArrayList<>();
        empresas = new ArrayList<>();

        for (int e = 0; e < 10; e++) {
            Empresa empresa = new Empresa(faker.company().name());
            EmpresasEntity empresasEntity = empresasBean.insertEmpresa(empresa);
            empresa.setId(empresasEntity.getId());
            empresas.add(empresa);

            for (int c = 0; c < 300; c++) {
                Conductor conductor = new Conductor(
                        faker.name().fullName(),
                        empresa,
                        new BigDecimal(faker.number().randomDouble(2, 4, 5)),
                        new BigDecimal(faker.number().randomDouble(2, 100, 100)),
                        new BigDecimal(faker.number().randomDouble(2, 140, 160)),
                        new BigDecimal(faker.number().randomDouble(2, 200, 200)),
                        new BigDecimal(faker.number().randomDouble(2, 10000, 1000))
                );

                ConductorEntity conductorEntity = conductoresBean.insertConductor(conductor);
                conductor.setId(conductorEntity.getId());
                conductores.add(conductor);
            }
        }



        /*
        if (false) {
            Faker faker = new Faker();
            vehiclesDbBean.truncate();
            workShiftsDbBean.truncate();
            conductoresDbBean.truncate();
            licensesDbBean.truncate();
            
            conductors = new ArrayList<>();
            for (int i = 0; i < 300; i++) {
                Conductor conductor = new Conductor(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.name().lastName(),
                        faker.idNumber().valid());

                EmployeeEntity e = conductoresDbBean.insertEmployee(conductor);
                conductor.setId(e.getId());
                conductors.add(conductor);
            }


            licenses = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                License license = new License(faker.idNumber().valid());
                LicenseEntity e = licensesDbBean.insertLicense(license);
                license.setId(e.getId());
                licenses.add(license);

                Vehicle vehicle = new Vehicle(faker.company().name(), faker.idNumber().valid(), license);
                VehicleEntity vehicleEntity = vehiclesDbBean.insertVehicle(vehicle);
            }


            workShifts = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                WorkShift workShift = new WorkShift();
                workShift.setId(faker.number().randomNumber());
                workShift.setShiftType(Math.random() > 0.5 ? ShiftType.T : ShiftType.M);
                Date day = faker.date().past(365, TimeUnit.DAYS);
                workShift.setDay(day);
                Random rand = new Random();
                workShift.setConductor(conductors.get(rand.nextInt(conductors.size())));
                workShift.setLicense(licenses.get(rand.nextInt(licenses.size())));
                workShift.setIncome(faker.number().randomDouble(2, 5, 200));

                workShifts.add(workShift);
                workShiftsDbBean.insertWorkShift(workShift);
            }
        }

         */
    }

}
