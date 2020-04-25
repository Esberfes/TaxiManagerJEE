package database;

import entities.EmpresasEntity;
import org.apache.log4j.Logger;
import pojos.Empresa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EmpresasDbBean {

    public final static String BEAN_NAME = "EmpresasDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    public EmpresasEntity insertEmpresa(Empresa empresa) {
        EmpresasEntity empresasEntity = new EmpresasEntity(empresa);
        em.persist(empresasEntity);

        return empresasEntity;
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM empresas WHERE true").executeUpdate();
    }
}
