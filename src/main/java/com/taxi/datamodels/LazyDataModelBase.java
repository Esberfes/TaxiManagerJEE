package com.taxi.datamodels;

import com.taxi.pojos.Identified;
import com.taxi.pojos.Recaudacion;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LazyDataModelBase<T extends Identified> extends LazyDataModel<T> {

    protected List<T> datasource;
    private LazyLoad<T> lazyLoad;
    private boolean updated;

    public LazyDataModelBase(LazyLoad<T> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    public void invalidate() {
        updated = false;
    }

    @Override
    public T getRowData(String rowKey) {
        for (T recaudacion : datasource) {
            if (String.valueOf(recaudacion.getId()).equals(rowKey)) {
                return recaudacion;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(T recaudacion) {
        return recaudacion.getId();
    }

    @Override
    public List<T> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
      if(!updated) {
          this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, new HashMap<>(filterMeta));
          this.setRowCount(this.lazyLoad.getTotal(new HashMap<>(filterMeta)));
      }

        return this.datasource;
    }
}
