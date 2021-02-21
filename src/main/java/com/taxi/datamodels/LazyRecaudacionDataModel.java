package com.taxi.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Recaudacion;

import java.util.List;
import java.util.Map;

public class LazyRecaudacionDataModel extends LazyDataModel<Recaudacion> {

    private List<Recaudacion> datasource;
    private LazyLoad<Recaudacion> lazyLoad;

    public LazyRecaudacionDataModel(LazyLoad<Recaudacion> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public Recaudacion getRowData(String rowKey) {
        for (Recaudacion recaudacion : datasource) {
            if (String.valueOf(recaudacion.getId()).equals(rowKey)) {
                return recaudacion;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Recaudacion recaudacion) {
        return recaudacion.getId();
    }

    @Override
    public List<Recaudacion> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}