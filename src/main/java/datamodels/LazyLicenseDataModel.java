package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.License;

import java.util.List;
import java.util.Map;

public class LazyLicenseDataModel extends LazyDataModel<License> {

    private List<License> datasource;
    private LazyLoad<License> lazyLoad;

    public LazyLicenseDataModel(LazyLoad<License> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public License getRowData(String rowKey) {
        for (License license : datasource) {
            if (String.valueOf(license.getId()).equals(rowKey)) {
                return license;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(License license) {
        return license.getId();
    }

    @Override
    public List<License> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}