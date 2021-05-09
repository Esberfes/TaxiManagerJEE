package com.taxi.business;

import com.taxi.database.RecaudacionDbBean;
import com.taxi.datamodels.LazyLoad;
import com.taxi.faces.SessionData;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Recaudacion;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = RecaudacionBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class RecaudacionBean implements LazyLoad<Recaudacion> {

    public static final String BEAN_NAME = "RecaudacionBean";

    @Inject
    private RecaudacionDbBean recaudacionDbBean;

    @Inject
    private SessionData sessionData;

    @Override
    public List<Recaudacion> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        filterMeta.put("mes", new FilterMeta("recaudaciones.mes", sessionData.getMes()));
        filterMeta.put("ano", new FilterMeta("recaudaciones.ano", sessionData.getAno()));
        return recaudacionDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(Recaudacion::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        filterMeta.put("mes", new FilterMeta("recaudaciones.mes", sessionData.getMes()));
        filterMeta.put("ano", new FilterMeta("recaudaciones.ano", sessionData.getAno()));
        return recaudacionDbBean.getTotal(filterMeta);
    }

    @Override
    public List<Recaudacion> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return 0;
    }

    public void update(Recaudacion recaudacion) {
        recaudacionDbBean.update(recaudacion);
    }

    public void insert(Recaudacion recaudacion) {
        recaudacionDbBean.insert(recaudacion);
    }

    public void delete(Long id) {
        recaudacionDbBean.delete(id);
    }

    public Recaudacion findSingle(Long id) {
        return new Recaudacion(recaudacionDbBean.findSingle(id));
    }
}
