package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.WorkShift;

import java.util.List;
import java.util.Map;

public class LazyWorkShiftDataModel extends LazyDataModel<WorkShift> {
    private List<WorkShift> datasource;
    private LazyLoad<WorkShift> lazyLoad;

    public LazyWorkShiftDataModel(LazyLoad<WorkShift> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public WorkShift getRowData(String rowKey) {
        for (WorkShift workShift : datasource) {
            if (String.valueOf(workShift.getId()).equals(rowKey)) {
                return workShift;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(WorkShift license) {
        return license.getId();
    }

    @Override
    public List<WorkShift> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}
