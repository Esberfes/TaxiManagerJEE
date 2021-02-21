package com.taxi.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.TiposGasto;

import java.util.List;
import java.util.Map;

public class LazyTiposGastoDataModel extends LazyDataModel<TiposGasto> {

    private List<TiposGasto> datasource;
    private LazyLoad<TiposGasto> lazyLoad;

    public LazyTiposGastoDataModel(LazyLoad<TiposGasto> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public TiposGasto getRowData(String rowKey) {
        for (TiposGasto tiposGasto : datasource) {
            if (String.valueOf(tiposGasto.getId()).equals(rowKey)) {
                return tiposGasto;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(TiposGasto tiposGasto) {
        return tiposGasto.getId();
    }

    @Override
    public List<TiposGasto> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}