package com.taxi.business;

import com.taxi.database.EmpresasDbBean;
import com.taxi.datamodels.LazyLoad;
import com.taxi.entities.EmpresasEntity;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Empresa;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = EmpresasBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class EmpresasBean implements LazyLoad<Empresa> {

    public static final String BEAN_NAME = "EmpresasBean";

    @Inject
    private EmpresasDbBean empresasDbBean;

    public EmpresasEntity insert(Empresa empresa) {
        return empresasDbBean.insert(empresa);
    }

    public void truncate() {
        empresasDbBean.truncate();
    }

    @Override
    public List<Empresa> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return empresasDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(Empresa::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return empresasDbBean.getTotal(filterMeta);
    }

    @Override
    public List<Empresa> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return 0;
    }

    @Override
    public Empresa findById(Long id) {
        return new Empresa(empresasDbBean.findById(id));
    }

    public Empresa getSingle(Long id) {
        return new Empresa(empresasDbBean.getSingleEmpresa(id));
    }

    public void update(Empresa empresa) {
        empresasDbBean.update(empresa);
    }

    public void delete(Long id) {
        empresasDbBean.delete(id);
    }

    public List<Empresa> findByName(String name) {
        return empresasDbBean.findByName(name).stream().map(Empresa::new).collect(Collectors.toList());
    }

    public Empresa findSingleByName(String name) {
        return new Empresa(empresasDbBean.findSingleByName(name));
    }
}
