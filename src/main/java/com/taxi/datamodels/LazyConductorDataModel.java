package com.taxi.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Conductor;

import java.util.List;
import java.util.Map;

public class LazyConductorDataModel extends LazyDataModel<Conductor> {

    private List<Conductor> datasource;
    private LazyLoad<Conductor> lazyLoad;

    public LazyConductorDataModel(LazyLoad<Conductor> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public Conductor getRowData(String rowKey) {
        for (Conductor conductor : datasource) {
            if (String.valueOf(conductor.getId()).equals(rowKey)) {
                return conductor;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Conductor conductor) {
        return conductor.getId();
    }

    @Override
    public List<Conductor> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}