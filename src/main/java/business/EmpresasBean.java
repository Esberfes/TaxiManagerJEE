package business;

import database.EmpresasDbBean;
import entities.EmpresasEntity;
import pojos.Empresa;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@LocalBean
@Stateless(name = EmpresasBean.BEAN_NAME)
public class EmpresasBean {

    public static final String BEAN_NAME = "EmpresasBean";

    @Inject
    private EmpresasDbBean empresasDbBean;

    public EmpresasEntity insertEmpresa(Empresa empresa) {
        return empresasDbBean.insertEmpresa(empresa);
    }

    public void truncate() {
        empresasDbBean.truncate();
    }
}
