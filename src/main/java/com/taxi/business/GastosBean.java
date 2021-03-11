package com.taxi.business;

import com.taxi.database.GastosDbBean;
import com.taxi.datamodels.LazyLoad;
import com.taxi.entities.GastosEntity;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Gasto;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = GastosBean.BEAN_NAME)
public class GastosBean implements LazyLoad<Gasto> {

    public static final String BEAN_NAME = "Gastosbean";

    @Inject
    private GastosDbBean gastosDbBean;

    @Override
    public List<Gasto> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return gastosDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(Gasto::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
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

    public void update(Gasto gasto) {
        gastosDbBean.update(gasto);
    }

    public void delete(Long id) {
        gastosDbBean.delete(id);
    }

    public void insert(Gasto gasto) {
        GastosEntity insert = gastosDbBean.insert(new GastosEntity(gasto));
        gasto.setId(gasto.getId());
    }
}
