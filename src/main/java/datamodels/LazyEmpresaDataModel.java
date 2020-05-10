package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.Empresa;

import java.util.List;
import java.util.Map;

public class LazyEmpresaDataModel extends LazyDataModel<Empresa> {

    private List<Empresa> datasource;
    private LazyLoad<Empresa> lazyLoad;

    public LazyEmpresaDataModel(LazyLoad<Empresa> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public Empresa getRowData(String rowKey) {
        for (Empresa empresa : datasource) {
            if (String.valueOf(empresa.getId()).equals(rowKey)) {
                return empresa;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Empresa conductor) {
        return conductor.getId();
    }

    @Override
    public List<Empresa> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}