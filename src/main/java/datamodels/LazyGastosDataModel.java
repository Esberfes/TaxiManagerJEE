package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.Gasto;

import java.util.List;
import java.util.Map;

public class LazyGastosDataModel extends LazyDataModel<Gasto> {

    private List<Gasto> datasource;
    private LazyLoad<Gasto> lazyLoad;

    public LazyGastosDataModel(LazyLoad<Gasto> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public Gasto getRowData(String rowKey) {
        for (Gasto gasto : datasource) {
            if (String.valueOf(gasto.getId()).equals(rowKey)) {
                return gasto;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Gasto gasto) {
        return gasto.getId();
    }

    @Override
    public List<Gasto> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}