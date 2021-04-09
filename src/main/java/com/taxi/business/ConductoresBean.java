package com.taxi.business;

import com.taxi.database.ConductoresDbBean;
import com.taxi.datamodels.LazyLoad;
import com.taxi.entities.ConductorEntity;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Conductor;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = ConductoresBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class ConductoresBean implements LazyLoad<Conductor> {

    public static final String BEAN_NAME = "ConductoresBean";

    private Map<String, String> columnsMapping;

    @PostConstruct
    public void init() {
        columnsMapping = new HashMap<>();
        columnsMapping.put("id", "id");
        columnsMapping.put("nombre", "nombre");
        columnsMapping.put("empresa", "empresa");
        columnsMapping.put("complementoIva", "complemento_iva");
        columnsMapping.put("t065", "t065");
        columnsMapping.put("t060", "t060");
        columnsMapping.put("t055", "t055");
        columnsMapping.put("t050", "t050");
    }

    @Inject
    private ConductoresDbBean conductoresDbBean;

    @Override
    public List<Conductor> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return conductoresDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(Conductor::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return conductoresDbBean.getTotal(filterMeta);
    }

    @Override
    public List<Conductor> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return 0;
    }

    public void update(Conductor conductor) {
        conductoresDbBean.update(conductor);
    }

    public void delete(Long id) {
        conductoresDbBean.delete(id);
    }

    public List<Conductor> findEmployeesByFullName(String name) {
        return conductoresDbBean.findByFullName(name).stream().map(Conductor::new).collect(Collectors.toList());
    }

    public ConductorEntity insert(Conductor conductor) {
        return conductoresDbBean.insert(conductor);
    }

    public void truncate() {
        conductoresDbBean.truncate();
    }
}
