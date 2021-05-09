package com.taxi.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.FormasPago;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LazyFormasPagoDataModel extends LazyDataModel<FormasPago> {

    private List<FormasPago> datasource;
    private LazyLoad<FormasPago> lazyLoad;

    public LazyFormasPagoDataModel(LazyLoad<FormasPago> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public FormasPago getRowData(String rowKey) {
        for (FormasPago formasPago : datasource) {
            if (String.valueOf(formasPago.getId()).equals(rowKey)) {
                return formasPago;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(FormasPago formasPago) {
        return formasPago.getId();
    }

    @Override
    public List<FormasPago> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, new HashMap<>(filterMeta));
        this.setRowCount(this.lazyLoad.getTotal(new HashMap<>(filterMeta)));

        return this.datasource;
    }
}