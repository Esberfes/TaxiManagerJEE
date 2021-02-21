package com.taxi.business;

import com.taxi.database.TiposGastoDbBean;
import com.taxi.datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.TiposGasto;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = TiposGastosBean.BEAN_NAME)
public class TiposGastosBean implements LazyLoad<TiposGasto> {

    public static final String BEAN_NAME = "TiposGastosBean";

    @Inject
    private TiposGastoDbBean tiposGastoDbBean;

    @Override
    public List<TiposGasto> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return tiposGastoDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(TiposGasto::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return tiposGastoDbBean.getTotal(filterMeta);
    }

    @Override
    public List<TiposGasto> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return 0;
    }

    public void delete(Long id) {
        tiposGastoDbBean.delete(id);
    }

    public void update(TiposGasto tiposGasto) {
        tiposGastoDbBean.update(tiposGasto);
    }

    public List<TiposGasto> findByName(String value) {
        return tiposGastoDbBean.findByName(value).stream().map(TiposGasto::new).collect(Collectors.toList());
    }

    public TiposGasto findSingleByName(String value) {
        return new TiposGasto(tiposGastoDbBean.findSingleByName(value));
    }

    public void insert(TiposGasto tiposGasto) {
        tiposGastoDbBean.insert(tiposGasto);
    }
}
