package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.Licencia;

import java.util.List;
import java.util.Map;

public class LazyLicenciaDataModel extends LazyDataModel<Licencia> {

    private List<Licencia> datasource;
    private LazyLoad<Licencia> lazyLoad;

    public LazyLicenciaDataModel(LazyLoad<Licencia> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public Licencia getRowData(String rowKey) {
        for (Licencia licencia : datasource) {
            if (String.valueOf(licencia.getId()).equals(rowKey)) {
                return licencia;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Licencia licencia) {
        return licencia.getId();
    }

    @Override
    public List<Licencia> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}