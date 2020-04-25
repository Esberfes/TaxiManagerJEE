package business.old;

public class LicensesBean {
    /*

    public static final String BEAN_NAME = "LicensesBean";

    @Inject
    private LicensesDbBean licensesDbBean;

    @Override
    public List<License> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return licensesDbBean.getLicenseData(first, pageSize, sortMeta, filterMeta).stream().map(License::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return licensesDbBean.getLicenseTotal(filterMeta);
    }

    public void update(License license) {
        licensesDbBean.updateLicense(license);
    }

    public void insertLicense(License license) {
        licensesDbBean.insertLicense(license);
    }

    public void deleteLicense(Long id) {
        licensesDbBean.deleteLicense(id);
    }

    public List<License> findLicensesByCod(String code) {
        return licensesDbBean.findLicensesByCod(code).stream().map(License::new).collect(Collectors.toList());
    }
    */

}
