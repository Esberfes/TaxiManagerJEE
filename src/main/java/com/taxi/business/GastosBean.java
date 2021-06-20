package com.taxi.business;

import com.taxi.database.GastosDbBean;
import com.taxi.datamodels.LazyLoad;
import com.taxi.entities.GastosEntity;
import com.taxi.faces.SessionData;
import com.taxi.pojos.TaxiFilterMeta;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Gasto;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = GastosBean.BEAN_NAME)
public class GastosBean implements LazyLoad<Gasto> {

    public static final String BEAN_NAME = "Gastosbean";

    @Inject
    private GastosDbBean gastosDbBean;

    @Inject
    private SessionData sessionData;

    public List<Gasto> getGastosComunes(int mes, int ano, Long id)    {
        return gastosDbBean.getGastosComunes(mes, ano, id);
    }

    @Override
    public List<Gasto> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        filterMeta.put("gastos.ano", new TaxiFilterMeta("gastos.ano", sessionData.getAno(), MatchMode.EXACT));
        filterMeta.put("gastos.mes", new TaxiFilterMeta("gastos.mes", sessionData.getMes(), MatchMode.EXACT));
        return gastosDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(Gasto::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        filterMeta.put("gastos.ano", new TaxiFilterMeta("gastos.ano", sessionData.getAno(), MatchMode.EXACT));
        filterMeta.put("gastos.mes", new TaxiFilterMeta("gastos.mes", sessionData.getMes(), MatchMode.EXACT));
        return gastosDbBean.getTotal(filterMeta);
    }

    @Override
    public List<Gasto> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return getData(first, pageSize, sortMeta, filterMeta);
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return getTotal(filterMeta);
    }

    @Override
    public Gasto findById(Long id) {
        return new Gasto(gastosDbBean.findById(id));
    }

    public void update(Gasto gasto) {
        gastosDbBean.update(gasto);
    }

    public void delete(Long id) {
        gastosDbBean.delete(id);
    }

    public void insert(Gasto gasto) {
        GastosEntity insert = gastosDbBean.insert(new GastosEntity(gasto));
        gasto.setId(insert.getId());
    }
}
