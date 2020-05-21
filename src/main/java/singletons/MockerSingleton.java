package singletons;

import business.ConductoresBean;
import business.EmpresasBean;
import business.LicenciasBean;
import com.github.javafaker.Faker;
import entities.ConductorEntity;
import entities.EmpresasEntity;
import entities.LicenciasEntity;
import pojos.Conductor;
import pojos.Empresa;
import pojos.Licencia;

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
    private List<Licencia> licencias;

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private EmpresasBean empresasBean;

    @Inject
    private LicenciasBean licenciasBean;

    @PostConstruct
    public void init() {
        if (false) {
            Faker faker = new Faker();

            licenciasBean.truncate();
            conductoresBean.truncate();
            empresasBean.truncate();

            licencias = new ArrayList<>(); // 300
            conductores = new ArrayList<>(); // 3000
            empresas = new ArrayList<>(); // 10

            for (int e = 0; e < 10; e++) {
                Empresa empresa = new Empresa(faker.company().name());
                EmpresasEntity empresasEntity = empresasBean.insert(empresa);
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

                    ConductorEntity conductorEntity = conductoresBean.insert(conductor);
                    conductor.setId(conductorEntity.getId());
                    conductores.add(conductor);
                }
            }

            for (Empresa empresa : empresas) {
                for (int i = 0; i < 30; i++) {
                    Licencia licencia = new Licencia();
                    licencia.setCodigo(Integer.parseInt(faker.number().digits(8)));
                    licencia.setEmpresa(empresa);
                    licencia.setEs_eurotaxi(Math.random() >= 0.5);
                    LicenciasEntity licenciasEntity = licenciasBean.insert(licencia);
                    licencia.setId(licenciasEntity.getId());

                    licencias.add(licencia);
                }
            }
        }

    }

}
