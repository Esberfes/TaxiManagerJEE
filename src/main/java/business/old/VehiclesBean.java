package business.old;

public class VehiclesBean {
/*
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
    */
}
