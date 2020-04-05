package datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import pojos.Vehicle;

import java.util.List;
import java.util.Map;

public class LazyVehicleDataModel extends LazyDataModel<Vehicle> {

    private List<Vehicle> datasource;
    private LazyLoad<Vehicle> lazyLoad;

    public LazyVehicleDataModel(LazyLoad<Vehicle> lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public Vehicle getRowData(String rowKey) {
        for (Vehicle vehicle : datasource) {
            if (String.valueOf(vehicle.getId()).equals(rowKey)) {
                return vehicle;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Vehicle vehicle) {
        return vehicle.getId();
    }

    @Override
    public List<Vehicle> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        this.datasource = this.lazyLoad.getData(first, pageSize, sortMeta, filterMeta);
        this.setRowCount(this.lazyLoad.getTotal(filterMeta));

        return this.datasource;
    }
}