package business;

import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.License;
import singletons.MockerSingleton;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless(name = LicensesBean.BEAN_NAME)
public class LicensesBean implements LazyLoad<License> {

    public static final String BEAN_NAME = "LicensesBean";

    @Inject
    private MockerSingleton mockerSingleton;

    @Override
    public List<License> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return mockerSingleton.getLicenseData(first, pageSize, sortMeta, filterMeta);
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return mockerSingleton.getLicenseTotal(filterMeta);
    }

    public void insertLicense(String code) {
        mockerSingleton.insertLicense(code);
    }

    public void deleteLicense(Long id) {
        mockerSingleton.deleteLicense(id);
    }
}
