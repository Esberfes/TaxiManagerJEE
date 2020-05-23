package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.RecaudacionIngreso;

import java.util.List;
import java.util.Map;

public class LazyRecaudacionIngresoDataModel extends LazyDataModel<RecaudacionIngreso> {

    private List<RecaudacionIngreso> datasource;
    private LazyLoad<RecaudacionIngreso> lazyLoad;
    private Long recaudacionId;

    public LazyRecaudacionIngresoDataModel(LazyLoad<RecaudacionIngreso> lazyLoad, Long recaudacionId) {
        this.lazyLoad = lazyLoad;
        this.recaudacionId = recaudacionId;
    }

    @Override
    public RecaudacionIngreso getRowData(String rowKey) {
        for (RecaudacionIngreso recaudacionIngreso : datasource) {
            if (String.valueOf(recaudacionIngreso.getId()).equals(rowKey)) {
                return recaudacionIngreso;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(RecaudacionIngreso recaudacionIngreso) {
        return recaudacionIngreso.getId();
    }

    @Override
    public List<RecaudacionIngreso> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta, recaudacionId);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta, recaudacionId));

        return this.datasource;
    }
}