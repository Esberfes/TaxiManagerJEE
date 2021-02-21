package com.taxi.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.ConceptosGastos;

import java.util.List;
import java.util.Map;

public class LazyConceptosGastosDataModel extends LazyDataModel<ConceptosGastos> {

    private List<ConceptosGastos> datasource;
    private LazyLoad<ConceptosGastos> lazyLoad;

    public LazyConceptosGastosDataModel(LazyLoad<ConceptosGastos> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public ConceptosGastos getRowData(String rowKey) {
        for (ConceptosGastos conceptosGastos : datasource) {
            if (String.valueOf(conceptosGastos.getId()).equals(rowKey)) {
                return conceptosGastos;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(ConceptosGastos conceptosGastos) {
        return conceptosGastos.getId();
    }

    @Override
    public List<ConceptosGastos> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}