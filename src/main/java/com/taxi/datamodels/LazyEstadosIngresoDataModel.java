package com.taxi.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.EstadosIngreso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LazyEstadosIngresoDataModel extends LazyDataModel<EstadosIngreso> {

    private List<EstadosIngreso> datasource;
    private LazyLoad<EstadosIngreso> lazyLoad;

    public LazyEstadosIngresoDataModel(LazyLoad<EstadosIngreso> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public EstadosIngreso getRowData(String rowKey) {
        for (EstadosIngreso estadosIngreso : datasource) {
            if (String.valueOf(estadosIngreso.getId()).equals(rowKey)) {
                return estadosIngreso;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(EstadosIngreso estadosIngreso) {
        return estadosIngreso.getId();
    }

    @Override
    public List<EstadosIngreso> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, new HashMap<>(filterMeta));
        this.setRowCount(this.lazyLoad.getTotal(new HashMap<>(filterMeta)));

        return this.datasource;
    }
}