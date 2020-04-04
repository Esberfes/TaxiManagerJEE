package business;

import database.LicensesDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.License;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = LicensesBean.BEAN_NAME)
public class LicensesBean implements LazyLoad<License> {

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
}
