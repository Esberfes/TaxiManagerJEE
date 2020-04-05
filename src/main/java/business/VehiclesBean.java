package business;

import database.LicensesDbBean;
import database.VehiclesDbBean;
import datamodels.LazyLoad;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.License;
import pojos.Vehicle;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = VehiclesBean.BEAN_NAME)
public class VehiclesBean implements LazyLoad<Vehicle> {

    public static final String BEAN_NAME = "VehiclesBean";

    @Inject
    private VehiclesDbBean vehiclesDbBean;

    @Inject
    private LicensesDbBean licensesDbBean;

    @Override
    public List<Vehicle> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return vehiclesDbBean.getVehiclesData(first, pageSize, sortMeta, filterMeta).stream().map(Vehicle::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return vehiclesDbBean.getTotalVehicles(filterMeta);
    }

    public void deleteVehicle(Long id) {
        vehiclesDbBean.deleteVehicle(id);
    }

    public void insertVehicle(String brand, String registration, String licenseCode) {
        License license = new License();
        if (StringUtils.isNotBlank(licenseCode)) {
            license = new License(licensesDbBean.findLicensesByCod(licenseCode).get(0));
        }

        vehiclesDbBean.insertVehicle(new Vehicle(brand, registration, license));
    }

    public void update(Vehicle vehicle) {
        vehiclesDbBean.update(vehicle);
    }
}
